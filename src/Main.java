import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.io.*;
import java.net.URL;

public class Main
{
    private final String TEMPLATES_FOLDER = "C:\\Users\\logoff\\IdeaProjects\\Find-My-Doppelganger\\ImageInput";
    private int matchCount = 0;

    public static void main(String[] args)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Main main = new Main();
        main.start();
    }

    void start()
    {
        System.out.println("Starting...");
        connectWebsite();
    }

    private void connectWebsite()
    {
        final String URL = "https://thispersondoesnotexist.com/";
        try
        {
            Document document = Jsoup.connect(URL).get();
            Element imageElement = document.selectFirst("img");
            String imageURL = imageElement.attr("abs:src");

            System.out.println("Connected to the website...");
            final int TEMPLATES_NUMBER = new File(TEMPLATES_FOLDER).list().length;

            int imgCount = 0;
            while (true)
            {
                downloadImage(imageURL);
                for (int j = 0; j < TEMPLATES_NUMBER; j++)
                    matchImages(String.format("face%d.png", j + 1));

                System.out.printf("Images searched: %d%n", ++imgCount);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void downloadImage(String strImageURL)
    {
        //get file name from image path
        String strImageName = strImageURL.substring(strImageURL.lastIndexOf("/") + 1);

        // System.out.println("Saving: " + strImageName + ", from: " + strImageURL);

        try
        {
            //open the stream from URL
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();

            byte[] buffer = new byte[4096];
            int n;

            OutputStream os = new FileOutputStream(strImageName + ".png");

            //write bytes to the output stream
            while ((n = in.read(buffer)) != -1)
            {
                os.write(buffer, 0, n);
            }

            //close the stream
            os.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void matchImages(String templateImage)
    {
        final String SOURCE_FOLDER = "C:\\Users\\logoff\\IdeaProjects\\Find-My-Doppelganger";

        Mat source;
        Mat template;
        //Load image file
        source = Imgcodecs.imread(String.format("%s\\image.png", SOURCE_FOLDER));
        template = Imgcodecs.imread(String.format("%s\\%s", TEMPLATES_FOLDER, templateImage));
        Mat outputImage = new Mat();
        int matchMethod = Imgproc.TM_CCOEFF_NORMED;
        //Template matching method
        Imgproc.matchTemplate(source, template, outputImage, matchMethod);

        Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        org.opencv.core.Point matchLoc = mmr.maxLoc;
        double threshold = 0.65;
        if (mmr.maxVal >= threshold && matchLoc.y > 10)
        {
            org.opencv.core.Point targetPoint = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());
            //Draw rectangle on result image
            Imgproc.rectangle(source, matchLoc, targetPoint, new Scalar(0, 0, 255), 1);

            //Save image
            final String MATCHES_FOLDER = "C:\\Users\\logoff\\IdeaProjects\\Find-My-Doppelganger\\PossibleMatches";
            Imgcodecs.imwrite(String.format("%s\\result%s(%d).png", MATCHES_FOLDER, mmr.maxVal, ++matchCount), source);
            System.out.printf("Match found! Max val: %s%n", mmr.maxVal);
        }
    }
}
