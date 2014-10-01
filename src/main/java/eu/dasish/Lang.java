package eu.dasish;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;

public class Lang {
	public static String detect(String text, String profilesPath) throws LangDetectException {
    	DetectorFactory.loadProfile(profilesPath);
    	Detector detector = DetectorFactory.create();
    	detector.append(text);
    	String language = detector.detect();
    	DetectorFactory.clear();
        return language;
    }
}
