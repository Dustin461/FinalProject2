package ProjectArticle;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class NhanDanArticle extends Application {


    /*
    public static void main(String[] args) throws IOException {
        ArrayList<Article> testList = getListOfElementsInNhanDan("https://nhandan.vn/vanhoa", "Others");
        testList.addAll(NhanDanArticle.getListOfElementsInNhanDan("https://nhandan.vn/xahoi", "Others"));
        testList.addAll(NhanDanArticle.getListOfElementsInNhanDan("https://nhandan.vn/du-lich", "Others"));
        testList.addAll(NhanDanArticle.getListOfElementsInNhanDan("https://nhandan.vn/giaoduc", "Others"));
        testList.addAll(NhanDanArticle.getListOfElementsInNhanDan("https://nhandan.vn/moi-truong", "Others"));
        System.out.println(testList);
        for (Article article:testList) {
            System.out.println(article.getDescription());
        }
        //launch(args);
    }
     */

    public static ArrayList<Article> getListOfElementsInNhanDan(String url, String category) throws IOException{
        ArrayList<Article> nhanDanArticleList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements articles;
            //Find articles
            articles = doc.select("div.uk-width-1-1.boxlist-other");
            //Find thumbnail of article
            Elements thumbnail = articles.select("div.box-img");
            //Find title of article
            Elements title = articles.select("div.box-title");
            //Find link of article
            Elements link = articles.select("div.box-title");
            //Find date of article
            Elements date = articles.select("div.box-meta-small");

            int maximumArticle = title.size();

            for (int i = 0, j = 0; i < maximumArticle; i++, j++){
                nhanDanArticleList.add(new Article());
                //Set title of the article
                nhanDanArticleList.get(i).setTitle(title.get(j).text());
                //Set source of the article
                nhanDanArticleList.get(i).setSource("NHANDAN.VN");
                //Set category of the article
                nhanDanArticleList.get(i).setCategory(category);
                //Set date of the article
                if(date.get(j).hasText()) {
                    nhanDanArticleList.get(i).setDate(Helper.timeToUnixString5(date.get(j).text()));
                }
                //Set time duration of the article
                nhanDanArticleList.get(i).setTimeDuration(Helper.timeDiff(Helper.timeToUnixString5(date.get(j).text())));
                //Set thumbnail of the article
                nhanDanArticleList.get(i).setThumbnail(thumbnail.get(j).select("img").attr("data-src"));
                //Set link of the article
                nhanDanArticleList.get(i).setLinkToArticle(link.get(j).select("a").attr("abs:href"));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound !");
            nhanDanArticleList.remove(nhanDanArticleList.size()-1);
        } catch (Selector.SelectorParseException e) {
            return null;
        }
        return nhanDanArticleList;
    }

    public static ArrayList<Article> getListOfSearchArticle(String keyword, String category) throws IOException{
        String url = "https://nhandan.vn/Search/" + keyword.trim().replaceAll("\\s", "%20").toLowerCase();
        ArrayList<Article> nhanDanSearchArticleList = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();

        try {
            Elements articles;
            //Find all articles having thumbnail
            articles = doc.select("div.boxlist-list article:has(div.box-img)");
            //Find thumbnail of article
            Elements thumbnail = articles.select("div.box-img img");
            //Find title of article
            Elements title = articles.select("div.box-title");
            //Find date of article
            Elements date = articles.select("div.box-meta-small");
            //Find link of article
            Elements link = articles.select("div.box-title");

            int maximumArticle = articles.size();

            for (int i = 0, j = 0; i< maximumArticle; i++, j++){
                nhanDanSearchArticleList.add(new Article());
                //Set title for article
                nhanDanSearchArticleList.get(i).setTitle(title.get(j).text());
                //Set source for article
                nhanDanSearchArticleList.get(i).setSource("NHANDAN.VN");
                //Set category for article
                nhanDanSearchArticleList.get(i).setCategory(category);
                //Set date for article
                if(date.get(j).hasText()) nhanDanSearchArticleList.get(i).setDate(Helper.timeToUnixString5(date.get(j).text()));
                //Set time duration for article
                nhanDanSearchArticleList.get(i).setTimeDuration(Helper.timeDiff(Helper.timeToUnixString5(date.get(j).text())));
                //Set thumbnail for article
                nhanDanSearchArticleList.get(i).setThumbnail(thumbnail.get(j).attr("abs:data-src"));
                //Set link for article
                nhanDanSearchArticleList.get(i).setLinkToArticle(link.get(j).select("a").attr("abs:href"));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound!");
            nhanDanSearchArticleList.remove(nhanDanSearchArticleList.size()-1);
        } catch (Selector.SelectorParseException e) {
            return null;
        }
        return nhanDanSearchArticleList;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Testing
        ArrayList<Article> testList = getListOfElementsInNhanDan("https://nhandan.vn", "Newest");
        System.out.println(testList);
        Article testArticle = testList.get(4); //<--- Set article index here
        VBox articlePage = new VBox();
        displayNhanDanArticle(testArticle, articlePage);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(articlePage);
        scrollPane.setStyle("-fx-background: #000000; -fx-border-color: #000000;");
        Scene scene = new Scene(scrollPane, 1300, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void displayNhanDanArticle(Article article, VBox vbox) throws IOException {
        vbox.getChildren().clear();
        try {
            Document doc = Jsoup.connect(article.getLinkToArticle()).get();
            Elements content = doc.select("div.detail-content-body");
            Elements body = content.select("> p, div.light-img");
            String thumbnail = doc.select("div.box-detail-thumb").select("img").attr("src");
            Elements author = doc.select("div.box-author");

            article.setAuthor(author.select("strong").text());

            // Display page logo
            Image logo = new Image("https://img.nhandan.com.vn/Files/Images/2021/08/04/logoND-1628062143340.png");
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
            // Normal texts
            Text cateAndDate = new Text("Category:  " + article.getCategory());
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
            // Normal texts
            Text titleText = new Text(article.getTitle());
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

            //Display thumbnail
            ImageView thumbView = new ImageView();
            thumbView.setCache(true);
            thumbView.setCacheHint(CacheHint.SPEED);
            thumbView.setImage(new Image(thumbnail, 800, 0, true, true));
            thumbView.setPreserveRatio(true);

            vbox.getChildren().addAll(thumbView, skipLine(1));

            // Display time & date & author
            // Normal texts
            String date = new java.util.Date(Long.parseLong(article.getDate())*1000).toString();
            Text timeAndDate = new Text("Date: " + date + " (" + article.getTimeDuration() + ")\n" + "Author: " + article.getAuthor());
            timeAndDate.setFill(Color.WHITE);
            timeAndDate.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 16));
            // HBox for time&date&author
            HBox timeAndDateHBox = new HBox(timeAndDate);
            timeAndDateHBox.setAlignment(Pos.BASELINE_LEFT);
            // Add HBox to VBox
            vbox.getChildren().add(timeAndDateHBox);
            vbox.getChildren().add(skipLine(15));

            // Display description
            // Normal texts
            Text description = new Text(article.getDescription());
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
                // Images
                if (part.attr("class").equals("light-img")) {
                    System.out.println(part.select("img").attr("src"));
                    // ImageViews
                    ImageView photoView = new ImageView();
                    photoView.setCache(true);
                    photoView.setCacheHint(CacheHint.SPEED);
                    photoView.setImage(new Image(part.select("img").attr("src"), 800, 0, true, true));
                    photoView.setPreserveRatio(true);

                    // Normal text for image caption
                    Text photoCaption = new Text("     " + part.select("figcaption.img-cap").select("em").text());
                    photoCaption.setFill(Color.WHITE);
                    photoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Add image view and image caption to VBox
                    vbox.getChildren().addAll(photoView, skipLine(1), photoCaption);
                }

                if(part.hasText()) {
                    Text paragraph = new Text(part.text());
                    paragraph.setFill(Color.WHITE);
                    paragraph.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 18));
                    // Text flow for paragraphs
                    TextFlow paragraphTextFlow = new TextFlow(paragraph);
                    // HBox for text flow
                    HBox paragraphHBox = new HBox(paragraphTextFlow);
                    paragraphHBox.setAlignment(Pos.BASELINE_LEFT);
                    paragraphHBox.setMaxSize(1000, 1000);
                    // Add to VBox
                    vbox.getChildren().add(paragraphHBox);
                    vbox.getChildren().add(skipLine(10));
                }
            }

            // Link to full article
            // Normal texts
            Text originalArticle = new Text("To original post here: ");
            originalArticle.setFill(Color.WHITE);
            originalArticle.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 18));
            // Hyperlink texts
            Hyperlink originalHyperLink = new Hyperlink("link.");
            originalHyperLink.setTextFill(Color.LIGHTPINK);
            originalHyperLink.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
            originalHyperLink.setUnderline(true);
            // Set action for hyperlink
            originalHyperLink.setOnAction(action -> {
                HostServices s = Helper.getInstance().getHostServices();
                s.showDocument(article.getLinkToArticle());
            });
            // Text flow for all
            TextFlow originalTextFlow = new TextFlow();
            originalTextFlow.getChildren().addAll(originalArticle, originalHyperLink);
            // Add all to VBox
            vbox.getChildren().add(originalTextFlow);
        } catch (Exception ignored) {}

    }

    public static Text skipLine(double spacing) {
        Text emptyLine = new Text("");
        emptyLine.setFont(Font.font("Times New Roman", spacing));
        return emptyLine;
    }

    public static HBox divider(String str) {
        Text div = new Text(str);
        div.setFill(Color.WHITE);
        div.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
        HBox divHBox = new HBox(div);
        divHBox.setAlignment(Pos.CENTER);
        return divHBox;
    }
}
