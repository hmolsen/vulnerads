package de.cqrity.vulnerapp.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import de.cqrity.vulnerapp.controller.ClassifiedAdFileResource;
import de.cqrity.vulnerapp.domain.ClassifiedAd;
import de.cqrity.vulnerapp.domain.ClassifiedAdResource;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import de.cqrity.vulnerapp.repository.UserRepository;
import de.cqrity.vulnerapp.xml.ClassifiedAdXmlDocument;

@Service
public class ClassifiedAdService {

	private static final String UPPER_FN = "UPPER";

	private static final Logger log = LoggerFactory.getLogger(ClassifiedAdService.class);

	@Autowired
	private ClassifiedAdRepository classifiedAdRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ClassifiedAd update(ClassifiedAdResource request) {
		ClassifiedAd ad = classifiedAdRepository.findById(request.getId());

		ad.setTitle(request.getTitle());
		ad.setDescription(request.getDescription());
		ad.setPrice(request.getPrice());
		if (!request.getAdphoto().isEmpty()) {
			String photofilename = imageService.storeImage(request.getAdphoto(), request.getId());
			ad.setPhotofilename(photofilename);
		}

		return classifiedAdRepository.save(ad);
	}

	public ClassifiedAd create(ClassifiedAdResource request) {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ClassifiedAd ad = new ClassifiedAd(principal, request.getTitle(), request.getDescription(), request.getPrice(),
				new Date());

		ad = classifiedAdRepository.save(ad); // to get the id

		ad.setPhotofilename(imageService.storeImage(request.getAdphoto(), ad.getId()));

		return classifiedAdRepository.save(ad);
	}

	public List<ClassifiedAd> fetchLatestAds(String searchString) {
		String searchStringWithWildcards = "%" + searchString + "%";

		String sql = "SELECT * FROM classified_ad WHERE " + UPPER_FN + "(title) LIKE " + UPPER_FN + "('"
				+ searchStringWithWildcards + "') " + "ORDER BY createdtimestamp DESC";

		RowMapper<ClassifiedAd> classifiedAdRowMapper = (rs, rowNum) -> {
			User owner = userRepository.findById(rs.getLong("OWNER_ID"));
			ClassifiedAd ad = new ClassifiedAd(owner, rs.getString("TITLE"), rs.getString("DESCRIPTION"),
					rs.getInt("PRICE"), rs.getTimestamp("CREATEDTIMESTAMP"));
			ad.setId(rs.getLong("ID"));
			ad.setPhotofilename(rs.getString("PHOTOFILENAME"));
			return ad;
		};

		return jdbcTemplate.query(sql, classifiedAdRowMapper);
	}

	public ClassifiedAd importAd(ClassifiedAdFileResource request) {
		ClassifiedAd ad;
		try {
			MultipartFile adfile = request.getAdfile();

			// Disable XXE
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setFeature("http://xml.org/sax/features/external-general-entities", false);
			spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			//Do unmarshall operation
			Source xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(),
					new InputSource(adfile.getInputStream()));
			
			JAXBContext jc = JAXBContext.newInstance(ClassifiedAdXmlDocument.class);
			Unmarshaller um = jc.createUnmarshaller();
			ClassifiedAdXmlDocument document = (ClassifiedAdXmlDocument) um.unmarshal(xmlSource);

			ad = document.getAd();
		} catch (JAXBException | IOException | SAXException | ParserConfigurationException e) {
			ad = null;
			log.error("Could not parse XML", e);
		}
		return ad;
	}

}
