package neil.rookieguide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import neil.guide.view.RookieGuide;
import neil.guide.view.target.RookieContextVLP;
import neil.guide.view.target.ViewGravity;


/**
 * Created by neil on 16/10/28.
 */
public class samples extends Activity{
    private Button myButton;
    private TextView myText;
    private Switch mySwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samples);
        myButton = (Button) findViewById(R.id.myButton);
        myText = (TextView) findViewById(R.id.myTextView);
        mySwitch = (Switch) findViewById(R.id.mySwitch);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        addRookieView();
    }

    protected void addRookieView(){
        RookieContextVLP rookieContextVLP = new RookieContextVLP(R.mipmap.rookie_info_tips, ViewGravity.BOTTOM);
        new RookieGuide.Builder(this)
                .setTarget(this.mySwitch)
                .setPositiveRes(R.mipmap.positive_button)
                .addTipsView(rookieContextVLP)
                .show();
    }

}
