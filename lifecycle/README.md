## Managing App's Lifecycle using "Lifecycle Aware components"

This sample demonstrate how an app's lifecycle can be managed using recently introduced 
lifecycle aware components. Classes that can work with [Lifecyle](https://developer.android.com/reference/android/arch/lifecycle/Lifecycle.html)
are known as lifecycle aware components.

These components helps app to :
* Survive configuration changes.
* Avoid memory leaks.
* Easy data loading into app's UI.

Above is achieved using these libraries:
* **[LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)**:
 LiveData is a data holder that keeps a value and allows it to be observed with in a specified lifecycle.

* **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)**: 
ViewModel class helps to store and manage data related to UI. Keeping UI related data in a separate 
ViewModel class helps to survive activity configuration data across screen rotation. Basically, ViewModel
class prepares and provide data for/to UI.

* **[LifecycleOwner](https://developer.android.com/reference/android/arch/lifecycle/LifecycleOwner.html)**: 
It's a class that has an Android Life cycle. Your Fragment/Activity may want to extend on of its
subclasses like LifecycleFragment/LifecycleActivity to incorporate automatic handling of Lifecycle events.

* **[LifecycleObserver](https://developer.android.com/reference/android/arch/lifecycle/LifecycleObserver.html)**: 
Marks a class as a LifecycleObserver.

***Useful links:***
* [Importing architecture components to your project](https://developer.android.com/topic/libraries/architecture/adding-components.html)

### Set up ###
I'm using Android Studio 3.0 Beta1.

`build.gradle` dependencies for ViewModel, Lifecycle and LiveData:
```
dependencies {
    compile "android.arch.lifecycle:runtime:1.0.0-alpha5"
    compile "android.arch.lifecycle:extensions:1.0.0-alpha5"
    annotationProcessor "android.arch.lifecycle:compiler:1.0.0-alpha5"
}
```

### Using ViewModel to retain data across lifecylce of Activity/Fragment ###

I've a sample app with a simple `TextView`. This textView displays a text 
'Hello Random Number: <generated random number>'. This number is generated and set in 
`onCreate()` method. 

### Before (Regular implementation) ###
Every time screen is rotated, `onCreate()` method is called and generates
a new random number.  This is how `HelloRandomNumberActivity` looks like:
```
public class HelloRandomNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_random_number);

        TextView textView = findViewById(R.id.helloRandomNumber);
        double randomNumber = Math.random();

        textView.setText("Hello Random Number: " + randomNumber);
    }
}
```

Feel free to try and run this code in action. You'll see every time you rotate your device's screen, 
a new random number is generated and displayed.

TODO: Add video here

### After (Using ViewModel to retain Activity config data)

I'll be using [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)
component to achieve the data persistence across screen rotations.

Here's is ViewModel to keep track of random number generated:
```
public class RandomNumberViewModel extends ViewModel {

    @Nullable
    private Double randomNumber;

    @Nullable
    public Double getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(Double randomNumber) {
        this.randomNumber = randomNumber;
    }
}
```

This is how `HelloRandomNumberActivity` looks like now:
```
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

```

***Note***: You may noticed that 'AppCompatActivity' is replaced with 'LifecycleActivity'.
This is because lifecycle classes are not not supported in support libraries. Once Lifecycle classes
are release, then it'll be available in Support library and so in 'AppCompatActivity'.

Try the code now ! There's no change in number every time screen is rotated.

TODO: //video here





