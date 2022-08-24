package com.meshalkina.coffee_machine;

import com.meshalkina.coffee_machine.dao.GoodsDAO;
import com.meshalkina.coffee_machine.model.Good;
import com.meshalkina.coffee_machine.model.GoodsInBasket;
import com.meshalkina.coffee_machine.service.BasketService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CoffeeMachineController implements Initializable {

    @FXML
    private TableView<Good> menu;

    @FXML
    private TableColumn<Good, Long> numMenu;

    @FXML
    private TableColumn<Good, String> nameMenu;

    @FXML
    private TableColumn<Good, Double> priceMenu;

    @FXML
    private TableView<GoodsInBasket> basket;

    @FXML
    private TableColumn<?, ?> numBasket;

    @FXML
    private TableColumn<?, ?> nameBasket;

    @FXML
    private TableColumn<?, ?> priceBasket;

    @FXML
    private TableColumn<?, ?> amountBasket;

    @FXML
    private TableColumn<?, ?> sumBasket;

    @FXML
    private TextField filterField;

    @FXML
    private Button searchButton;

    @FXML
    private Text totalSum;

    private GoodsDAO goodsDAO = new GoodsDAO();

    private BasketService basketService = new BasketService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        numMenu.setCellValueFactory(new PropertyValueFactory<>("goodId"));
        nameMenu.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceMenu.setCellValueFactory(new PropertyValueFactory<>("price"));
        menu.setItems(getGoodsList());

        numBasket.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameBasket.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceBasket.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountBasket.setCellValueFactory(new PropertyValueFactory<>("amount"));
        sumBasket.setCellValueFactory(new PropertyValueFactory<>("sum"));
        basket.setItems(getBasketList());

        totalSum.setText(String.valueOf(basketService.getSum()));

        menu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                {
                    basketService.putInBasket(observable.getValue());
                    basket.setItems(getBasketList());
                    basket.refresh();

                    totalSum.setText(String.valueOf(basketService.getSum()));
                }
        );

        searchButton.setOnAction(event -> {
            String query = filterField.getText().toLowerCase(Locale.ROOT).trim();

            ObservableList<Good> filteredObservableList = FXCollections.observableArrayList();
            List<Good> filteredGoodList = goodsDAO.findAll();

            if (!query.equals("")) {
                filteredGoodList = filteredGoodList.stream()
                        .filter(good -> good.getName().toLowerCase(Locale.ROOT).contains(query))
                        .collect(Collectors.toList());
            }

            filteredObservableList.addAll(filteredGoodList);
            menu.setItems(filteredObservableList);
            menu.refresh();
        });
    }


    private ObservableList<GoodsInBasket> getBasketList() {
        ObservableList<GoodsInBasket> basketList = FXCollections.observableArrayList();

        int count = 0;

        for (Map.Entry<Long, Map<Integer, Integer>> entry : basketService.getBasket().entrySet()) {
            GoodsInBasket goodsInBasket = new GoodsInBasket();
            goodsInBasket.setId(++count);

            Good good = goodsDAO.findById(entry.getKey());
            goodsInBasket.setName(good.getName());
            goodsInBasket.setPrice(good.getPrice());
            goodsInBasket.setAmount(entry.getValue().get(entry.getValue().keySet().iterator().next()));
            goodsInBasket.setSum(goodsInBasket.getPrice() * goodsInBasket.getAmount());

            basketList.add(goodsInBasket);
        }

        return basketList;
    }

    public ObservableList<Good> getGoodsList() {

        ObservableList<Good> menuList = FXCollections.observableArrayList();

        menuList.addAll(goodsDAO.findAll());

        return menuList;
    }

    public void openNewStage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CoffeeMachineController.class.getResource("payWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 250);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Payment window");
        stage.show();

        final Node source = (Node) event.getSource();
        final Stage stage1 = (Stage) source.getScene().getWindow();
        stage1.close();
    }

    public void clearBasket(ActionEvent event) {
        basketService.deleteAllFromBasket();
        basket.setItems(getBasketList());
        basket.refresh();
        totalSum.setText(String.valueOf(basketService.getSum()));
    }

}