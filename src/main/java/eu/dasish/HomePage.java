package eu.dasish;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class HomePage extends BasePage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		final UploadForm progressUploadForm = new UploadForm(
                "progressUpload");

        progressUploadForm.add(new UploadProgressBar("progress",
                progressUploadForm, progressUploadForm.uploadField));
        add(progressUploadForm);
        
        final Form<String> textForm = new Form<String>("textForm", new Model<String>(""));
		
		textForm.add(new TextArea<String>("textarea", textForm.getModel()));
		
		textForm.add(new AjaxSubmitLink("submitLink") {
			private static final long serialVersionUID = 4860874580978387733L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				final PageParameters params = new PageParameters();
				params.add("input", form.getModelObject() != null ? form.getModelObject().toString() : "");
				setResponsePage(ResultPage.class, params);
			}
		});
		
		add(textForm);
    }
	
	private class UploadForm extends Form<Void> {
	    private static final long serialVersionUID = 8993195018595404342L;

	    private FileUploadField uploadField;

	    public UploadForm(String id) {
	        super(id);
	        setMultiPart(true);
	        add(uploadField = new FileUploadField("fileInput"));
	    }

	    @Override
	    protected void onSubmit() {
	        final List<FileUpload> uploads = uploadField.getFileUploads();
	        if (uploads != null) {
	            final FileUpload upload = uploadField.getFileUpload();
	            final PageParameters params = new PageParameters();
	            params.add("input", new String(upload.getBytes()));
	            setResponsePage(ResultPage.class, params);
	        }
	    }
	}
}