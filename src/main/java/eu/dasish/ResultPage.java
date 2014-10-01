package eu.dasish;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ResultPage extends BasePage {
	private static final long serialVersionUID = 5315316233899445501L;

	private static String[] freelingLanguages = { "en", "ca", "es", "pt", "it",
			"gl", "as", "cy" };
	private static String danish = "da";
	private static String swedish = "sv";

	private void doWork(final String input, final String email) {
		final String profilesPath = ResultPage.class.getClassLoader()
				.getResource("profiles").getPath();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					PageParameters params = new PageParameters();
					params.add("email", email);

					String language = Lang.detect(input, profilesPath);
					if (language.equals(danish)) {
						NER.danish(input, email);
					} else if (language.equals(swedish)) {
						String projectName = NER.stagger(input);
						params.add("id", projectName);
						params.add("url", NER.StaggerURL);
						setResponsePage(FinalPage.class, params);
					} else {
						boolean found = false;
						for (String lang : freelingLanguages) {
							if (lang.equals(language)) {
								found = true;
								String projectName = NER.freeling(input, lang);
								params.add("id", projectName);
								params.add("url", NER.FreelingURL);
								setResponsePage(FinalPage.class, params);
							}
						}
						if (!found) {
							System.out
									.println(">>> Unsupported language detected: "
											+ language);
						}
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

		}).run();
	}

	public ResultPage(final PageParameters parameters) {
		Form<String> form = new Form<String>("emailForm", new Model<String>(""));
		form.add(new TextField<String>("email", form.getModel()));
		form.add(new AjaxSubmitLink("submitLink") {
			private static final long serialVersionUID = 4860874580978387733L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String input = parameters.get("input").toString();
				String email = (String) form.getModel().getObject();
				doWork(input, email);
			}
		});
		add(form);
	}

}
