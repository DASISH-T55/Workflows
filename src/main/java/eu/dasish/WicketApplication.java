package eu.dasish;

import javax.servlet.ServletContext;

import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.file.Folder;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see eu.dasish.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	private Folder uploadFolder = null;
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}
	
	public Folder getUploadFolder() {
        return uploadFolder;
    }

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		getResourceSettings().setThrowExceptionOnMissingResource(false);

        ServletContext context = WicketApplication.get().getServletContext();
        String path = context.getRealPath("/");

        uploadFolder = path != null ? new Folder(path)
                                    : new Folder(System.getProperty("java.io.tmpdir"), "wicket-uploads");

        System.out.println("> [WicketApplication] upload path set to: " + uploadFolder.getAbsolutePath());

        // Ensure folder exists
        uploadFolder.mkdirs();

        getRootRequestMapperAsCompound().add(
                new MountedMapper("/single", HomePage.class));

        getApplicationSettings().setUploadProgressUpdatesEnabled(true);
	}
}
