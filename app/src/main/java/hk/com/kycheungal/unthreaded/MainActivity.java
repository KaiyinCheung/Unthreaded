package hk.com.kycheungal.unthreaded;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressBar progressBar;
    private TextView statusText;
    private int completed;

    //1.2
    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        statusText = (TextView) findViewById(R.id.status_text);

        progressBar.setMax(100);

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);



        //Start the background thread
        workerthread.start();
    }

    private Runnable worker = new Runnable() {
        @Override
        public void run() {
            int l;
            completed = 0;

            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(completed);
                    statusText.setText(String.format("Completed %d",completed));
                }
            });

            for (int i = 0; i<100; ++i){
                for (int j =0; j<1000; ++j){
                    for (int k = 0; k<1000; ++k){
                        l = i*j*k;
                    }
                }
                completed += 1;
            }


        }
    };

    //declare a new background thread
    Thread workerthread = new Thread(worker);


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.start_button):
                if (workerthread == null){
                    completed = 0;
                    workerthread = new Thread(worker);
                    workerthread.start();
                }


            case (R.id.reset_button):
                if (workerthread != null){
                    workerthread.interrupt();
                    workerthread = null;
                }
                progressBar.setProgress(0);
                statusText.setText(String.format("Click the button"));

        }

    }
}
