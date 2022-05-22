/*
    RMIT University Vietnam
    Course: INTE2512 Object-Oriented Programming
    Semester: 2021B
    Assessment: Final Project
    Author:
    - Pham Duy Anh - s3802674
    - Pham Dang Khoa - s3884419
    - Nguyen Minh Hien - s3877996
    - Nathan Candre - s3938364
    Acknowledgement:
    [1]: https://jsoup.org/cookbook/extracting-data/selector-syntax
    [2]: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html
    [3]: https://www.tutorialspoint.com/javafx/javafx_css.htm
    [4]: https://www.javatpoint.com/javafx-playing-video
    [5] All lecture and lab slides from RMIT univeristy
*/
package ProjectController;

import ProjectArticle.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeSceneController implements Initializable {
    @FXML
    private Button BusinessButton;

    @FXML
    private Button CovidButton;

    @FXML
    private Button EntertainmentButton;

    @FXML
    private Button HealthButton;

    @FXML
    private Button NewestButton;

    @FXML
    private Button OthersButton;

    @FXML
    private Button PoliticsButton;

    @FXML
    private Button SportButton;

    @FXML
    private Button WorldButton;

    @FXML
    private BorderPane borderPaneUnderScrollPane;

    @FXML
    private Button closeButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button reloadButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button technologyButton;

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void reload(ActionEvent event) {
        switch (currentCategoryIndex) {
            case 0 -> displayNewestList();
            case 1 -> displayCovidList();
            case 2 -> displayPoliticsList();
            case 3 -> displayBusinessList();
            case 4 -> displayTechnologyList();
            case 5 -> displayHealthList();
            case 6 -> displaySportsList();
            case 7 -> displayEntertainmentList();
            case 8 -> displayWorldList();
            case 9 -> displayOthersList();
            case 10 -> search();
            default -> {
            }
        }
    }

    @FXML
    void toHome(ActionEvent event) {

    }
    @FXML
    void displayBusinessList(ActionEvent event) {
        displayBusinessList();
    }

    @FXML
    void displayCovidList(ActionEvent event) {
        displayCovidList();
    }

    @FXML
    void displayEntertainmentList(ActionEvent event) {
        displayEntertainmentList();
    }

    @FXML
    void displayHealthList(ActionEvent event) {
        displayHealthList();
    }

    @FXML
    void displayNewestList(ActionEvent event) {
        displayNewestList();
    }

    @FXML
    void displayOthersList(ActionEvent event) {
        displayOthersList();
    }

    @FXML
    void displayPoliticsList(ActionEvent event) {
        displayPoliticsList();
    }

    @FXML
    void displaySportsList(ActionEvent event) {
        displaySportsList();
    }

    @FXML
    void displayTechnologyList(ActionEvent event) {
        displayTechnologyList();
    }

    @FXML
    void displayWorldList(ActionEvent event) {
        displayWorldList();
    }


    @FXML
    void search(ActionEvent event) {
        search();
    }

    public String searchText;

    public static ProgressBar progressBar = new ProgressBar();
    public static ProgressBar newestProgressBar = new ProgressBar();

    // Pagination control
    public int currentCategoryIndex = 0, currentArticleIndex = 0;
    public ArrayList<Article> currentCategoryList;
    public Pagination currentPagination;

    // Instance Layout controller
    public LayoutController layoutController;

    // Component to full articles
    public VBox fullArticleVbox = new VBox();
    public StackPane stackPane1 = new StackPane();
    public StackPane stackPane2 = new StackPane();
    public VBox displayLayoutVbox = new VBox();

    // Loading pane
    public StackPane loadingStackPane = new StackPane();
    ImageView loadingIconImageView;
    Image loadingIcon;

    public HomeSceneController() {
        // Setup for layout controller
        // Setup fxml loader

        FXMLLoader loader = new FXMLLoader(HomeSceneController.class.getResource("/ProjectController/Layout.fxml"));

        // Get the vbox root of the layout.fxml
        try {
            displayLayoutVbox = loader.load();
            displayLayoutVbox.setCache(true);
            displayLayoutVbox.setCacheHint(CacheHint.SPEED);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the LayoutController instance of layout.fxml
        layoutController = loader.getController();

        //Set the progress bar
        progressBar.setProgress(0.0);
        progressBar.setPrefHeight(5);
        progressBar.setPrefWidth(200);
        newestProgressBar.setProgress(0.0);
        newestProgressBar.setPrefHeight(5);
        newestProgressBar.setPrefWidth(200);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Display Newest category
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));

        // Full articles VBox <-- changeable
        fullArticleVbox.setMinHeight(985);
        fullArticleVbox.setSpacing(20);
        fullArticleVbox.setAlignment(Pos.TOP_CENTER);
        fullArticleVbox.setPadding(new Insets(100, 0, 100, 0));
        fullArticleVbox.setCache(true);
        fullArticleVbox.setCacheHint(CacheHint.SPEED);

        // stackPane1 format
        stackPane1.setAlignment(Pos.TOP_CENTER);
        stackPane1.setStyle("-fx-background: #000000; -fx-border-color: #000000;"); // BG: black, Border: black <-- changeable

        // stackPane2 format
        stackPane2.setAlignment(Pos.TOP_CENTER);
        stackPane2.setMaxWidth(1200);
        stackPane2.setMinWidth(1200);
        stackPane2.setStyle("-fx-background: #000000; -fx-border-color: #000000;"); // BG: black, Border: black <-- changeable
        stackPane2.getChildren().add(fullArticleVbox);
        stackPane2.setVisible(false);
        stackPane1.getChildren().add(stackPane2);
    }
    //Display the newest list on a borderpane
    public void displayNewestList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            try {
                ArticleList.getNewestList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display Covid List on a borderpane
    public void displayCovidList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            ArticleList.getCovidList();
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display Politics List on a borderpane
    public void displayPoliticsList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            ArticleList.getPoliticsList();
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display Business List on a borderpane
    public void displayBusinessList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            try {
                ArticleList.getBusinessList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display Technology List on a borderpane
    public void displayTechnologyList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            try {
                ArticleList.getTechnologyList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display Health List on a borderpane
    public void displayHealthList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            try {
                ArticleList.getHealthList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display Sport List on a borderpane
    public void displaySportsList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            try {
                ArticleList.getSportsList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display Entertainment List on a borderpane
    public void displayEntertainmentList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            try {
                ArticleList.getEntertainmentList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display World List on a borderpane
    public void displayWorldList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            try {
                ArticleList.getWorldList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display Other List on a borderpane
    public void displayOthersList() {
        scrollPane.setVvalue(0);
        scrollPane.setHvalue(0);
        currentCategoryIndex = 0;
        stackPane2.setVisible(false);
        borderPaneUnderScrollPane.setCenter(null);
        borderPaneUnderScrollPane.setCenter(loadingStackPane);
        Thread t1 = new Thread(() -> {
            try {
                ArticleList.getOthersList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                borderPaneUnderScrollPane.setCenter(null);
                borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.newestList, new Pagination()));
                System.gc();
                Runtime.getRuntime().gc();
            });
        });
        t1.start();
    }

    // Display Search List on a borderpane
    public void search() {
        if (!searchText.isEmpty()) {
            scrollPane.setVvalue(0);
            scrollPane.setHvalue(0);
            currentCategoryIndex = 10;
            stackPane2.setVisible(false);
            borderPaneUnderScrollPane.setCenter(null);
            borderPaneUnderScrollPane.setCenter(loadingStackPane);
            Thread t1 = new Thread(() -> {
                try {
                    homeButton.setVisible(false);
                    ArticleList.getSearchList(searchText.trim());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    borderPaneUnderScrollPane.setCenter(null);
                    borderPaneUnderScrollPane.setCenter(setPaginationList(ArticleList.searchList, new Pagination()));
                    System.gc();
                    Runtime.getRuntime().gc();
                });
            });
            t1.start();
        }
    }

    // Set Articles into pages
    public Pagination setPaginationList(ArrayList<Article> articlesList, Pagination newPagination) {
        newPagination.setMaxHeight(983);
        newPagination.setMinHeight(983);
        newPagination.setMaxPageIndicatorCount(5);

        currentCategoryList = articlesList;
        int size = (int) Math.floor((double) currentCategoryList.size() / 10.0);
        newPagination.setPageCount(size);

        if (size < 1) {
            newPagination.setPageCount(5);
            newPagination.setPageFactory(pageIndex -> {
                Text emptyPageText = new Text("No articles here");
                emptyPageText.setFill(Color.BLACK);
                emptyPageText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 40));
                emptyPageText.setTextAlignment(TextAlignment.JUSTIFY);
                return emptyPageText;
            });

            System.gc();
            Runtime.getRuntime().gc();
            return newPagination;
        }

        currentPagination = newPagination;

        AnchorPane[] anchorPaneList = {layoutController.anchorpane1, layoutController.anchorpane2, layoutController.anchorpane3, layoutController.anchorpane4, layoutController.anchorpane5, layoutController.anchorpane6, layoutController.anchorpane7, layoutController.anchorpane8, layoutController.anchorpane9, layoutController.anchorpane10};
        Text[] textSourceList = {layoutController.textsource1, layoutController.textsource2, layoutController.textsource3, layoutController.textsource4, layoutController.textsource5, layoutController.textsource6, layoutController.textsource7, layoutController.textsource8, layoutController.textsource9, layoutController.textsource10};
        Text[] textTitleList = {layoutController.texttitle1, layoutController.texttitle2, layoutController.texttitle3, layoutController.texttitle4, layoutController.texttitle5, layoutController.texttitle6, layoutController.texttitle7, layoutController.texttitle8, layoutController.texttitle9, layoutController.texttitle10};
        Button[] buttonList = {layoutController.button1, layoutController.button2, layoutController.button3, layoutController.button4, layoutController.button5, layoutController.button6, layoutController.button7, layoutController.button8, layoutController.button9, layoutController.button10};

        newPagination.setPageFactory(pageIndex -> {
            for (int i = 10 * pageIndex, k = 0; i < 10 + 10 * pageIndex; i++, k++) {
                int finalArticleIndex = i;
                
                // Set title text for each article
                textTitleList[k].setText(articlesList.get(i).getTitle());

                // Set source text + time ago + source image + action event for each button for each article
                switch (articlesList.get(i).getSource()) {
                    case "VNEXPRESS.NET" -> {
                        textSourceList[k].setText("Báo VnExpress - " + articlesList.get(i).getTimeDuration());
                        buttonList[k].setOnAction(e -> {
                            fullArticleVbox.getChildren().clear();
                            borderPaneUnderScrollPane.setCenter(null);
                            borderPaneUnderScrollPane.setCenter(loadingStackPane);
                            Thread t1 = new Thread(() -> {
                                try {
                                    VnExpressArticle.displayVEArticle(articlesList.get(finalArticleIndex), fullArticleVbox);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                Platform.runLater(() -> {
                                    borderPaneUnderScrollPane.setCenter(null);
                                    borderPaneUnderScrollPane.setCenter(stackPane1);
                                    stackPane2.setVisible(true);
                                    System.gc();
                                    Runtime.getRuntime().gc();
                                });
                            });
                            t1.start();
                            currentArticleIndex = finalArticleIndex;
                        });
                    }
                    case "ZINGNEW.VN" -> {
                        textSourceList[k].setText("Báo ZingNews - " + articlesList.get(i).getTimeDuration());
                        buttonList[k].setOnAction(e -> {
                            fullArticleVbox.getChildren().clear();
                            borderPaneUnderScrollPane.setCenter(null);
                            borderPaneUnderScrollPane.setCenter(loadingStackPane);
                            Thread t1 = new Thread(() -> {
                                try {
                                    ZingArticle.displayZingArticle(articlesList.get(finalArticleIndex), fullArticleVbox);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                Platform.runLater(() -> {
                                    borderPaneUnderScrollPane.setCenter(null);
                                    borderPaneUnderScrollPane.setCenter(stackPane1);
                                    stackPane2.setVisible(true);
                                    System.gc();
                                    Runtime.getRuntime().gc();
                                });
                            });
                            t1.start();
                            currentArticleIndex = finalArticleIndex;
                        });
                    }
                    case "TUOITRE.VN" -> {
                        textSourceList[k].setText("Báo Tuổi Trẻ - " + articlesList.get(i).getTimeDuration());
                        buttonList[k].setOnAction(e -> {
                            fullArticleVbox.getChildren().clear();
                            borderPaneUnderScrollPane.setCenter(null);
                            borderPaneUnderScrollPane.setCenter(loadingStackPane);
                            Thread t1 = new Thread(() -> {
                                try {
                                    TuoiTreArticle.displayTTArticle(articlesList.get(finalArticleIndex), fullArticleVbox);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                Platform.runLater(() -> {
                                    borderPaneUnderScrollPane.setCenter(null);
                                    borderPaneUnderScrollPane.setCenter(stackPane1);
                                    stackPane2.setVisible(true);
                                    System.gc();
                                    Runtime.getRuntime().gc();
                                });
                            });
                            t1.start();
                            currentArticleIndex = finalArticleIndex;
                        });
                    }
                    case "NHANDAN.VN" -> {
                        textSourceList[k].setText("Báo Nhân Dân - " + articlesList.get(i).getTimeDuration());
                        buttonList[k].setOnAction(e -> {
                            fullArticleVbox.getChildren().clear();
                            borderPaneUnderScrollPane.setCenter(null);
                            borderPaneUnderScrollPane.setCenter(loadingStackPane);
                            Thread t1 = new Thread(() -> {
                                try {
                                    NhanDanArticle.displayNhanDanArticle(articlesList.get(finalArticleIndex), fullArticleVbox);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                Platform.runLater(() -> {
                                    borderPaneUnderScrollPane.setCenter(null);
                                    borderPaneUnderScrollPane.setCenter(stackPane1);
                                    stackPane2.setVisible(true);
                                    System.gc();
                                    Runtime.getRuntime().gc();
                                });
                            });
                            t1.start();
                            currentArticleIndex = finalArticleIndex;
                        });
                    }
                    case "THANHNIEN.VN" -> {
                        textSourceList[k].setText("Báo Thanh Niên - " + articlesList.get(i).getTimeDuration());
                        buttonList[k].setOnAction(e -> {
                            fullArticleVbox.getChildren().clear();
                            borderPaneUnderScrollPane.setCenter(null);
                            borderPaneUnderScrollPane.setCenter(loadingStackPane);
                            Thread t1 = new Thread(() -> {
                                try {
                                    ThanhNienArticle.displayTNArticle(articlesList.get(finalArticleIndex), fullArticleVbox);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                Platform.runLater(() -> {
                                    borderPaneUnderScrollPane.setCenter(null);
                                    borderPaneUnderScrollPane.setCenter(stackPane1);
                                    stackPane2.setVisible(true);
                                    System.gc();
                                    Runtime.getRuntime().gc();
                                });
                            });
                            t1.start();
                            currentArticleIndex = finalArticleIndex;
                        });
                    }
                }

                // Set thumbnail image for each object
                if (k != 3 && k != 7) {
                    Image image = new Image(articlesList.get(i).getThumbnail(), 600, 0, true, true, true);
                    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
                    Background background = new Background(backgroundImage);
                    anchorPaneList[k].setCache(true);
                    anchorPaneList[k].setCacheHint(CacheHint.SPEED);
                    anchorPaneList[k].setBackground(background);
                } else {
                    Image image2 = new Image(articlesList.get(i).getThumbnail(), 600, 0, true, true, true);
                    BackgroundImage backgroundImage2 = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
                    Background background2 = new Background(backgroundImage2);
                    layoutController.anchorPaneImage1.setCache(true);
                    layoutController.anchorPaneImage1.setCacheHint(CacheHint.SPEED);
                    layoutController.anchorPaneImage2.setCache(true);
                    layoutController.anchorPaneImage2.setCacheHint(CacheHint.SPEED);
                    if (k == 3) layoutController.anchorPaneImage1.setBackground(background2);
                    else layoutController.anchorPaneImage2.setBackground(background2);
                }

            }
            return displayLayoutVbox;
        });

        System.gc();
        Runtime.getRuntime().gc();
        return newPagination;
    }

}
