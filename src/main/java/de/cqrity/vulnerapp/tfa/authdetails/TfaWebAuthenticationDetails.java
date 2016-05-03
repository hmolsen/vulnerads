package de.cqrity.vulnerapp.tfa.authdetails;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TfaWebAuthenticationDetails extends WebAuthenticationDetails {

    private Integer tfaKey;

    /**
     * Records the remote address and will also set the session Id if a session
     * already exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public TfaWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String tfaKeyString = request.getParameter("tfakey");
        if (StringUtils.hasText(tfaKeyString)) {
            try {
                this.tfaKey = Integer.valueOf(tfaKeyString);
            } catch (NumberFormatException e) {
                this.tfaKey = null;
            }
        }
    }

    public Integer getTfaKey() {
        return tfaKey;
    }
}
