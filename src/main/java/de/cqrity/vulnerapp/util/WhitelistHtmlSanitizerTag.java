package de.cqrity.vulnerapp.util;


import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class WhitelistHtmlSanitizerTag extends SimpleTagSupport{
    private static PolicyFactory policy = new HtmlPolicyBuilder()
            .allowElements("br")
            .toFactory();

    private String untrustedHtml;

    private String sanitizedHtml() {
        return policy.sanitize(untrustedHtml);
    }

    public void setUntrustedHtml(String html) {
        this.untrustedHtml = html;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.print(sanitizedHtml());
    }
}
