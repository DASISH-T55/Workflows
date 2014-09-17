package eu.dasish;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class FinalPage extends BasePage {
	private static final long serialVersionUID = -5574667200962001528L;

	public FinalPage(final PageParameters parameters) {
		add(new Label("email", parameters.get("email").toString()));
		add(new ExternalLink("reviewLink", "" + parameters.get("url") + parameters.get("id")));
		add(new ExternalLink("zipLink", "" + parameters.get("url") + parameters.get("id") + "/output/zip"));
	}
}
