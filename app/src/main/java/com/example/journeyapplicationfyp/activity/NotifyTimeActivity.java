package com.example.journeyapplicationfyp.activity;

/**
 * @author Aaron Hastings
 *
 * Copyright 2015-2019 Aaron Hastings
 *
 * This file is part of Luas at a Glance.
 *
 * Luas at a Glance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Luas at a Glance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Luas at a Glance.  If not, see <http://www.gnu.org/licenses/>.
 */



import androidx.fragment.app.FragmentActivity;

public class NotifyTimeActivity extends FragmentActivity {

  /*  private final String LOG_TAG = NotifyTimeActivity.class.getSimpleName();

    private Map<String, Integer> mapNotifyTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String DIALOG = "dialog";

        /*
         * If the user is on Lollipop or above, use a Material Dialog theme. Otherwise, fall back to
         * the default theme set in AndroidManifest.xml.
         */
     /*   if (Build.VERSION.SDK_INT >= 21)
            setTheme(android.R.style.Theme_Material_Dialog);

        /* This is a Dialog. Get rid of the default Window title. */
      /*  requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notify_time);

        String localeDefault = Locale.getDefault().toString();

        mapNotifyTimes = new NotifyTimesMap(localeDefault, DIALOG);

        final Spinner spinnerNotifyTime = findViewById(R.id.spinner_notifytime);
        ArrayAdapter adapterNotifyTime = ArrayAdapter.createFromResource(
                getApplicationContext(), R.array.array_notifytime_mins, R.layout.spinner_notify_time
        );
        adapterNotifyTime.setDropDownViewResource(R.layout.spinner_notify_time);
        spinnerNotifyTime.setAdapter(adapterNotifyTime);

        /* Set the Spinner's colour to Luas purple. */
       /* Drawable spinnerDrawable =
                spinnerNotifyTime.getBackground().getConstantState().newDrawable();
        spinnerDrawable.setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.luas_purple),
                PorterDuff.Mode.SRC_ATOP
        );
        spinnerNotifyTime.setBackground(spinnerDrawable);

        Button buttonNotifyTime = findViewById(R.id.button_notifytime);
        buttonNotifyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Create an Intent to send the user-selected notification time back to
                 * LineFragment.
                 */
               /* Intent intent = new Intent();
                intent.setPackage(getPackageName());
                //intent.setClass(getApplicationContext(), NotifyTimesReceiver.class);
                intent.setAction(NotifyTimeActivity.class.getName());
                intent.putExtra(
                        Constant.NOTIFY_STOP_NAME,
                        Preferences.notifyStopName(getApplicationContext())
                );

                intent.putExtra(
                        Constant.NOTIFY_TIME,
                        mapNotifyTimes.get(spinnerNotifyTime.getSelectedItem().toString())
                );

                /* Send the Intent. */
              /*  sendBroadcast(intent);

                Analytics.selectContent(
                        getApplicationContext(),
                        "schedule_created",
                        "schedule_created"
                );

                /* Dismiss the Dialog. */
                /*finish();
            }
        });
    }
}*/
}

