package co.ostorlab.insecure_app.bugs.calls;

import android.content.Context;
import java.io.FileOutputStream;
import android.util.Xml;
import org.xmlpull.v1.XmlSerializer;


import co.ostorlab.insecure_app.BugRule;

public class XmlInjection extends BugRule {

    @Override
    public void run(String user_input) throws Exception {

        createXmlFile(user_input);
    }

    @Override
    public String getDescription() {
        return "Inject an xml resource.";
    }

    private void createXmlFile(String user_input) throws Exception {
        // Create a new XML file in the app's internal storage
        FileOutputStream fos = getContext().openFileOutput("injected_data.xml", Context.MODE_PRIVATE);

        // Create an XmlSerializer
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(fos, "UTF-8");

        // Start the XML document
        serializer.startDocument(null, Boolean.TRUE);

        // Start the root element
        serializer.startTag(null, "root");

        // Inject the sanitized user input into the XML
        serializer.text(user_input);

        // End the root element
        serializer.endTag(null, "root");

        // End the XML document
        serializer.endDocument();

        // Close the output stream
        fos.close();
    }
}


