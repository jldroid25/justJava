package com.nevictus.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

// This app displays an order form to order coffee.


public class MainActivity extends AppCompatActivity {

    // Global variable for increment & decrement method
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //This method is called when the quantity increment plus button is clicked
    public void increment(View view) {
        //Let's prevent no more than 100 cups of coffee orders, so when we reach 100 stop adding.

        if(quantity == 100){
            //Show an error pop up message to user if trying to get  more than 100 cups
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this method early there is nothing else to do.
            return;
        }
        quantity = quantity + 1;
        displayPlusQnt(quantity);

    }


    //This method is called when the quantity decrement minus  button is clicked
    public void decrement(View view) {
        //Let's prevent negative number of orders, so quantity must equal to 1 or greater.
        if( quantity == 1){
            //Show an error pop up message to user if trying to get less than 1 cup
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early there is nothing else to do.
            return;
        }
        quantity = quantity - 1;
        displayMinusQnt(quantity);

    }


    /**
     * This method is called when the order button is clicked.
     * Also inside of this method we're also calling other methods
     */
    public void submitOrder(View view) {


               /* Defining a whipped Cream object of the type checkbox & find its view id in xml */
                CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);

                // Now lets call the hasWhippedCream on the Object WhippedCreamCheckBox
                boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();

                // Defining a chocolate object of the type checkbox & find its view id in xml.
                CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);

                // Now lets call a method has Chocolate on the Object ChocolateCheckBox
                boolean hasChocolate = ChocolateCheckBox.isChecked();

                // Defining an nameField object of the type EditText to get users input
                EditText customerName = (EditText) findViewById(R.id.nameField);

                // Defining method to get user input or retrieving the value
                String custName = customerName.getText().toString();

                int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Defining method to display parameters price, whippedCream & chocolate
        String ordSum = createOrderSummary(price, hasWhippedCream, hasChocolate);

                String priceMessage = getString(R.string.order_summary_name, custName) +
                        "\n" + ordSum + "\n" + getString(R.string.add_whippedCream) + "\t" + hasWhippedCream +
                        "\n" + getString(R.string.add_chocolate)+ "\t" + hasChocolate + "\n" +
                        "Total = " + "$" + price + "\n" + getString(R.string.thank_you);


                //Create an intent Object to send action to open email app
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            //Let's add the customer's name on the email subject field & some text.
            intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for:  " + custName);
            //Let's add the order summary in the body of the email
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);


        //Check if there is an activity on the device to send intent. If not skip the intent.
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);

        }

    }


    /**
     * Define Calculate the price of the order
     *
     * @param addWhippedCream is weather or not the user wants whipped cream toppin.
     * @param addChocolate    is wheather or not the user wants chocolate topping
     * @return total price
     * @
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        /*Price of 1 cup of coffee */
        int basePrice = 5;

        // Add  $1 if user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // add $2 if user wants chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        //Calculate the total order price by multiplying by quantity
        return basePrice * quantity;
    }

    /**
     * Define an Order Summary method
     *
     * @return customer name quantity, &  order name
     * @param_addWhippedCream is whether or not the user wants whipped cream topping.
     * @param_addChocolate is whether or not the user wants chocolate topping.
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate) {
        return getString(R.string.quantity) +":\t" + quantity;
    }


    //This method displays the given price on the screen.
    private void displayPrice(int number) {
        TextView price_text_view = (TextView) findViewById(R.id.order_summary_text_view);
        price_text_view.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    //This method displays the increment() for + button on the screen.
    private void displayPlusQnt(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.quantity_text_view);
        priceTextView.setText("" + number);
    }

    //This method displays the increment() for - button on the screen.
    private void displayMinusQnt(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.quantity_text_view);
        priceTextView.setText("" + number);
    }

}
