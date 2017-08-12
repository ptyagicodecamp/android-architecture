package org.pcc.lifecycle;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

/**
 * Created by ptyagi on 8/11/17.
 */

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
