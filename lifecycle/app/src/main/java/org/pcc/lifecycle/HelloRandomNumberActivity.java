package org.pcc.lifecycle;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.TextView;

public class HelloRandomNumberActivity extends LifecycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_random_number);

        //ViewModelStore provides a new ViewModel or one previously created.
        RandomNumberViewModel randomNumberViewModel =
                ViewModelProviders.of(this).get(RandomNumberViewModel.class);

        TextView textView = findViewById(R.id.helloRandomNumber);
        String text = "Hello Random Number: ";

        if (randomNumberViewModel.getRandomNumber() == null) {
            double randomNumber = Math.random();
            //assigning value to ViewModel
            randomNumberViewModel.setRandomNumber(randomNumber);
            textView.setText(text + randomNumber);
        } else {
            //display data from previous retained ViewModel
            textView.setText(text + randomNumberViewModel.getRandomNumber());
        }
    }
}
