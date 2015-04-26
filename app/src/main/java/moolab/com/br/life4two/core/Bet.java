package moolab.com.br.life4two.core;

import android.content.Context;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import moolab.com.br.life4two.core.model.BetOption;
import moolab.com.br.life4two.parsecloud.ParseConfig;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucasmontano on 26/04/15.
 */
public class Bet {

    private final Context context;

    public interface BetCallback {

        public void onCreateBet(boolean result);
    }

    public Bet(Context context) {
        this.context = context;
    }

    public void create(final List<BetOption> options, final BetCallback callback) {

        ParseUser parseUser = ParseUser.getCurrentUser();
        parseUser.getQuery().include(ParseKeysMaster.BOO);
        parseUser.getQuery().getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                ParseObject bet = new ParseObject(ParseKeysMaster.OBJECT_BET);

                ParseRelation relation = bet.getRelation(ParseKeysMaster.OPTIONS);

                for (BetOption option : options)
                    relation.add(option);

                bet.put(ParseKeysMaster.BY, parseUser);
                if (parseUser.getParseUser(ParseKeysMaster.BOO) != null) {
                    bet.put(ParseKeysMaster.TO, parseUser.getParseUser(ParseKeysMaster.BOO));
                }
                bet.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        callback.onCreateBet(e == null);

                        if (e != null)
                            Log.e("Bet Error", e.getMessage());
                    }
                });
            }
        });
    }
}
