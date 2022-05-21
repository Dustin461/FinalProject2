package ProjectArticle;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.io.IOException;
import java.util.ArrayList;

public class ThanhNienArticle extends Application {
    public static String getTNArticleTime(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements articleDate = doc.select("div.meta span.time");
        return articleDate.text().replace("- ", "");
    }

    public static String getTNArticleDescription(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements articleDescription = doc.select("div#chapeau.sapo.cms-desc");
        return articleDescription.text();
    }

    //Get the list of the newest articles
    public static ArrayList<Article> getTNArticleNewest(String url, String category) throws IOException {
        ArrayList<Article> newestList = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        //Find the newest articles
        Elements articles = doc.select("item");
        //Find title of article
        Elements title = doc.select("title");
        //Find thumbnail of article
        Elements thumbnail = doc.select("image");
        //Find link of article
        Elements link = doc.select("link");
        //Find date of article
        Elements date = doc.select("pubDate");

        try {
            for(int i = 0, j = 0; i < articles.size(); i++, j++) {
                newestList.add(new Article());

                //Set source of article
                newestList.get(i).setSource("THANHNIEN.VN");
                //Set category of article
                newestList.get(i).setCategory(category);
                //Set title of article
                newestList.get(i).setTitle(title.get(j).text());
                //Set date of article
                newestList.get(i).setDate(Helper.timeToUnixString2(date.get(j).text()));
                //Set time duration of article
                newestList.get(i).setDate(Helper.timeDiff(Helper.timeToUnixString2(date.get(j).text())));
                //Set link to article
                newestList.get(i).setLinkToArticle(link.get(j).text());
                //Set thumbnail of article
                String descriptionToText = articles.get(i).select("description").text();
                int startIndex = descriptionToText.indexOf("src=\"") + 5; //Get start index of link of thumbnail
                int endIndex = descriptionToText.indexOf("\"", startIndex) - 1; //Get end index of link of thumbnail
                String linkOfThumbnail = descriptionToText.substring(startIndex, endIndex - 1);
                newestList.get(i).setThumbnail(linkOfThumbnail);
            }

        } catch (Selector.SelectorParseException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            newestList.remove(newestList.size() - 1);
        }
        return newestList;
    }

    public static ArrayList<Article> getListOfElementsInTN(String url, String category) throws IOException {
        ArrayList<Article> thanhnienArticleList = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        try {
            //Find list of articles
            Elements articles = doc.select("div.relative article.story, div.feature article.story.story--primary");
            //Find title and link of article
            Elements titleAndLink = articles.select("a.story__title");
            //Find thumbnail of article
            Elements thumbNail = articles.select("a.story__thumb img");
            //Find date of article
            Elements date = articles.select("time[rel]");
            //Find description of article
            Elements description = articles.select("div.summary p");

            int maximumArticle = articles.size();
            if(category.equals("Sport")) maximumArticle = 8;

            for (int i = 0, j = 0; i < maximumArticle; i++, j++) {
                // New article
                thanhnienArticleList.add(new Article());
                // Add title
                thanhnienArticleList.get(i).setTitle(titleAndLink.get(j).text());
                // Add source
                thanhnienArticleList.get(i).setSource("THANHNIEN.VN");
                // Add category
                thanhnienArticleList.get(i).setCategory(category);
                // Add date&time
                String dateTemp = date.get(j).attr("rel").substring(0, 10);
                long dateTemp0 = Long.parseLong(dateTemp) - 25200;
                dateTemp = String.valueOf(dateTemp0);
                thanhnienArticleList.get(i).setDate(dateTemp);
                // Add time duration
                thanhnienArticleList.get(i).setTimeDuration(Helper.timeDiff(dateTemp));
                // Add thumbnail
                if (thumbNail.get(i).hasAttr("data-src"))
                    thanhnienArticleList.get(i).setThumbnail(thumbNail.get(j).attr("abs:data-src"));
                else {
                    thanhnienArticleList.get(i).setThumbnail(thumbNail.get(j).attr("abs:src"));
                }
                // Add description
                thanhnienArticleList.get(i).setDescription(description.get(j).text());
                // Add link to article
                thanhnienArticleList.get(i).setLinkToArticle(titleAndLink.get(j).attr("abs:href"));
            }
        }
        catch (Selector.SelectorParseException e) {
            return null;
        }
        catch (IndexOutOfBoundsException e) {
            thanhnienArticleList.remove(thanhnienArticleList.size()-1);
        }
        return thanhnienArticleList;
    }

    public static ArrayList<Article> getListOfSearchTNArticle(String keyword, String category) throws IOException {
        final int MAX_ARTICLES = 50;
        ArrayList<Article> listOfSearchArticle = new ArrayList<>();

        String convertedKeyword = "https://thanhnien.vn/tim-kiem/?q=" + keyword.trim().replaceAll("\\s", "%20").toLowerCase();
        Document doc = Jsoup.connect(convertedKeyword).get();

        try {
            //Find list of articles
            Elements articles = doc.select("div.relative article.story");
            //Find title and link of article
            Elements titleAndLink = articles.select("a.story__title");
            //Find thumbnail of article
            Elements thumbNail = articles.select("a.story__thumb img");
            //Find date of article
            Elements date = articles.select("div.meta span.time");
            //Find description of article
            Elements description = articles.select("div.summary p");

            int maximumArticle = articles.size();

            for (int i = 0, j = 0; i < maximumArticle; i++, j++) {
                // New article
                listOfSearchArticle.add(new Article());
                // Add title
                listOfSearchArticle.get(i).setTitle(titleAndLink.get(j).text());
                // Add source
                listOfSearchArticle.get(i).setSource("THANHNIEN.VN");
                // Add category
                listOfSearchArticle.get(i).setCategory(category);
                // Add date&time
                listOfSearchArticle.get(i).setDate(Helper.timeToUnixString5(date.get(j).text()));
                // Add time duration
                listOfSearchArticle.get(i).setTimeDuration(Helper.timeDiff(Helper.timeToUnixString5(date.get(j).text())));
                // Add thumbnail
                if (thumbNail.get(i).hasAttr("data-src"))
                    listOfSearchArticle.get(i).setThumbnail(thumbNail.get(j).attr("abs:data-src"));
                else {
                    listOfSearchArticle.get(i).setThumbnail(thumbNail.get(j).attr("abs:src"));
                }
                // Add description
                listOfSearchArticle.get(i).setDescription(description.get(j).text());
                // Add link to article
                listOfSearchArticle.get(i).setLinkToArticle(titleAndLink.get(j).attr("abs:href"));
            }
        }
        catch (Selector.SelectorParseException e) {
            return null;
        }
        catch (IndexOutOfBoundsException e) {
            listOfSearchArticle.remove(listOfSearchArticle.size()-1);
        }
        return listOfSearchArticle;

    }

    public static void displayTNArticle(Article a, VBox vbox) throws IOException {
        vbox.getChildren().clear();

        Document doc = Jsoup.connect(a.getLinkToArticle()).get();

        try {
            Elements content = doc.select("div#abody.cms-body.detail"); // Content
            Elements body = content.select("p, table"); // p + image + video
            Elements author = doc.select("#storybox > div.details__author > div > div > h4 > a"); // author
            Elements pageCategory = doc.select
                    ("#st-container > div > div.site-content.media-content > div.l-grid > div.breadcrumbs > a:nth-child(2)"); // page category

            // Add author
            a.setAuthor(author.text());
            // Add page category
            a.setPageCategory(pageCategory.text());

            // Display category
            // Normal texts (Color: WHITE, Font: Helvetica, FontWeight: THIN, FontPosture: ITALIC, Size: 30)
            Text categoryText = new Text("---" + a.getCategory() + "---");
            categoryText.setFill(Color.WHITE);
            categoryText.setFont(Font.font("Helvetica", FontWeight.THIN, FontPosture.ITALIC, 30));
            // HBox for category
            HBox categoryHBox = new HBox(categoryText);
            categoryHBox.setAlignment(Pos.CENTER);
            // Add HBox to Vbox
            vbox.getChildren().add(categoryHBox);

            // Display page logo
            // Image & ImageView
            Image logo = new Image("https://amthanhxehoi.com/wp-content/uploads/2020/04/logo-baothanhnien.png");
            ImageView logoView = new ImageView();
            logoView.setCache(true);
            logoView.setCacheHint(CacheHint.SPEED);
            logoView.setImage(logo);
            logoView.setFitHeight(300);
            logoView.setFitWidth(700);
            logoView.setPreserveRatio(true);
            // HBox for logo view
            HBox logoViewHBox = new HBox(logoView);
            logoViewHBox.setAlignment(Pos.CENTER);
            // Add HBox to VBox
            vbox.getChildren().add(logoViewHBox);
            vbox.getChildren().add(skipLine(10));

            // Display page category
            // Normal texts (Color: WHITE, Font: Helvetica, FontWeight: MEDIUM, FontPosture: REGULAR, Size: 20)
            Text cateAndDate = new Text("Category:  " + a.getPageCategory());
            cateAndDate.setFill(Color.WHITE);
            cateAndDate.setFont(Font.font("Helvetica", FontWeight.MEDIUM, FontPosture.REGULAR, 20));
            // HBox for page category
            HBox cateAndDateHBox = new HBox(cateAndDate);
            cateAndDateHBox.setAlignment(Pos.CENTER);
            // Add HBox to VBox
            vbox.getChildren().add(cateAndDateHBox);
            vbox.getChildren().add(divider("-----/-----"));
            vbox.getChildren().add(skipLine(10));

            // Display title
            // Normal texts (Color: LIGHTBLUE, Font: Verdana, FontWeight: EXTRA_BOLD, FontPosture: REGULAR, Size: 35)
            Text titleText = new Text(a.getTitle());
            titleText.setFill(Color.LIGHTBLUE);
            titleText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 35));
            // Text flow for title
            TextFlow titleTextFlow = new TextFlow(titleText);
            titleTextFlow.setTextAlignment(TextAlignment.LEFT);
            // HBox for text flow
            HBox titleHBox = new HBox(titleTextFlow);
            titleHBox.setAlignment(Pos.BASELINE_LEFT);
            titleHBox.setMaxSize(1200, 1200);
            // Add HBox to VBox
            vbox.getChildren().add(titleHBox);

            // Display time & date & author
            // Normal texts (Color: WHITE, Font: Verdana, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
            Text timeAndDate = new Text("Date: " + a.getDate().replace(" ", "  -  ") + " (" + a.getTimeDuration() + ")\n"
                    + "Author: " + a.getAuthor());
            timeAndDate.setFill(Color.WHITE);
            timeAndDate.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 16));
            // HBox for time&date&author
            HBox timeAndDateHBox = new HBox(timeAndDate);
            timeAndDateHBox.setAlignment(Pos.BASELINE_LEFT);
            // Add HBox to VBox
            vbox.getChildren().add(timeAndDateHBox);
            vbox.getChildren().add(skipLine(15));

            // Display description
            // Normal texts (Color: GREY, Font: Verdana, FontWeight: BOLD, FontPosture: ITALIC, Size: 18)
            Text description = new Text(a.getDescription());
            description.setFill(Color.GREY);
            description.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
            // Text flow for description
            TextFlow descriptionTextFlow = new TextFlow(description);
            descriptionTextFlow.setTextAlignment(TextAlignment.LEFT);
            // HBox for description
            HBox descriptionHBox = new HBox(descriptionTextFlow);
            descriptionHBox.setAlignment(Pos.BASELINE_LEFT);
            descriptionHBox.setMaxSize(1000, 1000);
            // Add HBox to Vbox
            vbox.getChildren().add(descriptionHBox);

            for (Element part : body) {
                vbox.getChildren().add(skipLine(15));

                if (part.hasClass("video")) {
                    // Normal texts (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
                    Text videoText = new Text("Watch video here: ");
                    videoText.setFill(Color.WHITE);
                    videoText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Hyperlink texts (Color: LIGHTPINK, Font: Times New Roman, FontWeight: BOLD, FontPosture: ITALIC, Size: 18)
                    String linkToVideo = part.select("div.clearfix.cms-video").attr("data-video-src");
                    Hyperlink videoHyperlink = new Hyperlink("link.");
                    videoHyperlink.setTextFill(Color.LIGHTPINK);
                    videoHyperlink.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
                    videoHyperlink.setUnderline(true);
                    // Set action for hyperlink
                    videoHyperlink.setOnAction(action -> {
                        HostServices s = Helper.getInstance().getHostServices();
                        s.showDocument(linkToVideo);
                    });
                    // Video caption normal texts (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
                    Text videoCaption = new Text(" (Caption: " + part.select("table.video td.caption").text() + ")");
                    videoCaption.setFill(Color.WHITE);
                    videoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Text flow for all
                    TextFlow videoTextFlow = new TextFlow();
                    videoTextFlow.getChildren().addAll(videoText, videoHyperlink, videoCaption);
                    // Add all to VBox
                    vbox.getChildren().add(videoTextFlow);
                }

                if (part.hasClass("picture")) {
                    Elements temp = part.select("td.pic img");
                    // ImageViews
                    ImageView photoView = new ImageView();
                    photoView.setCache(true);
                    photoView.setCacheHint(CacheHint.SPEED);
                    if (temp.hasAttr("data-src")) {
                        photoView.setImage(new Image(part.select("img").attr("data-src"), 800, 0, true, true));
                    }
                    else if (temp.hasAttr("src")) {
                        photoView.setImage(new Image(part.select("img").attr("src"), 800, 0, true, true));
                    }

                    // Normal text for image caption  (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
                    Text photoCaption = new Text("Caption: " + part.select("td.caption").text());
                    photoCaption.setFill(Color.WHITE);
                    photoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Text flow for image caption
                    TextFlow photoCaptionTextFow = new TextFlow(photoCaption);
                    // HBox for text flow
                    HBox photoCationHBox = new HBox(photoCaptionTextFow);
                    photoCationHBox.setAlignment(Pos.BASELINE_LEFT);
                    photoCationHBox.setMaxSize(1000, 1000);
                    // Add image view and image caption to VBox
                    vbox.getChildren().addAll(photoView, skipLine(1), photoCationHBox);
                }

                if (part.hasText() && !part.hasClass("picture") && !part.hasClass("video") && !part.parent().hasClass("caption") && !part.parent().hasClass("source")) {
                    // Normal text for paragraphs  (Color: WHITE, Font: Times New Roman, FontWeight: NORMAL, FontPosture: REGULAR, Size: 20)
                    Text paragraph = new Text("     " + part.text());
                    paragraph.setFill(Color.WHITE);
                    paragraph.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    // Text flow for paragraphs
                    TextFlow paragraphTextFlow = new TextFlow(paragraph);
                    // HBox for text flow
                    HBox paragraphHBox = new HBox(paragraphTextFlow);
                    paragraphHBox.setAlignment(Pos.BASELINE_LEFT);
                    paragraphHBox.setMaxSize(1000, 1000);
                    // Add to VBox
                    vbox.getChildren().add(paragraphHBox);
                }
            }

            vbox.getChildren().add(divider("-----/-----"));
            vbox.getChildren().add(skipLine(10));

            // Link to full article
            // Normal texts (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 18)
            Text originalArticle = new Text("To original post here: ");
            originalArticle.setFill(Color.WHITE);
            originalArticle.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 18));
            // Hyperlink texts (Color: LIGHTPINK, Font: Times New Roman, FontWeight: BOLD, FontPosture: ITALIC, Size: 18)
            Hyperlink originalHyperLink = new Hyperlink("link.");
            originalHyperLink.setTextFill(Color.LIGHTPINK);
            originalHyperLink.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
            originalHyperLink.setUnderline(true);
            // Set action for hyperlink
            originalHyperLink.setOnAction(action -> {
                HostServices s = Helper.getInstance().getHostServices();
                s.showDocument(a.getLinkToArticle());});
            // Text flow for all
            TextFlow originalTextFlow = new TextFlow();
            originalTextFlow.getChildren().addAll(originalArticle, originalHyperLink);
            // Add all to VBox
            vbox.getChildren().add(originalTextFlow);
        }
        catch (Selector.SelectorParseException ignored) {}
    }

    // Return an HBox that have a dividing string
    public static HBox divider(String str) {
        Text div = new Text(str);
        div.setFill(Color.WHITE);
        div.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
        HBox divHBox = new HBox(div);
        divHBox.setAlignment(Pos.CENTER);
        return divHBox;
    }

    // Return empty text to simulate 'enter'
    public static Text skipLine(double spacing) {
        Text emptyLine = new Text("");
        emptyLine.setFont(Font.font("Times New Roman", spacing));
        return emptyLine;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
