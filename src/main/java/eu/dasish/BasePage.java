package eu.dasish;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BasePage extends WebPage {
	private static final long serialVersionUID = 4242149340979780055L;

	public BasePage() {
		this(new PageParameters());
	}
	
	public BasePage(final PageParameters parameters) {
		add(new BookmarkablePageLink<Void>("homeLink", HomePage.class));
		add(new BookmarkablePageLink<Void>("infoLink", InformationPage.class));
		add(new BookmarkablePageLink<Void>("contactLink", ContactPage.class));
	}
}
