package moolab.com.br.life4two.core;

import android.content.Context;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import moolab.com.br.life4two.core.model.BetModel;
import moolab.com.br.life4two.core.model.BetOptionModel;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucasmontano on 26/04/15.
 */
public class Bet {

    private final Context context;

    public enum BetStatus {
        WAITING, IN_PROGRESS, FINISHED;
    }

    public interface CreateBetCallback {

        public void onCreateBet(boolean result);
    }

    public interface GetBetCallback {

        public void onLoad(BetModel bet);
    }

    public Bet(Context context) {
        this.context = context;
    }

    public void create(final List<BetOptionModel> options, final String reward, final CreateBetCallback callback) {

        ParseUser parseUser = ParseUser.getCurrentUser();
        parseUser.getQuery().include(ParseKeysMaster.BOO);
        parseUser.getQuery().getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                BetModel bet = new BetModel();

                ParseRelation relation = bet.getRelation(ParseKeysMaster.OPTIONS);

                for (BetOptionModel option : options)
                    relation.add(option);

                bet.put(ParseKeysMaster.BY, parseUser);
                if (parseUser.getParseUser(ParseKeysMaster.BOO) != null) {
                    bet.put(ParseKeysMaster.TO, parseUser.getParseUser(ParseKeysMaster.BOO));
                }
                bet.put(ParseKeysMaster.REWARD, reward);
                bet.put(ParseKeysMaster.STATUS, BetStatus.WAITING);
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

    public void getToDo(final GetBetCallback callback) {
        ParseQuery query = ParseQuery.getQuery(ParseKeysMaster.OBJECT_BET);
        query.whereEqualTo(ParseKeysMaster.STATUS, BetStatus.WAITING);
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.getFirstInBackground(new GetCallback() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                callback.onLoad((BetModel) parseObject);
            }

            @Override
            public void done(Object o, Throwable throwable) {
                callback.onLoad((BetModel) o);
            }
        });
    }

    public void getLastBet(final GetBetCallback callback) {
        ParseQuery query = ParseQuery.getQuery(ParseKeysMaster.OBJECT_BET);
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.orderByDescending(ParseKeysMaster.UPDATED_AT);
        query.getFirstInBackground(new GetCallback() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                callback.onLoad((BetModel) parseObject);
            }

            @Override
            public void done(Object o, Throwable throwable) {
                callback.onLoad((BetModel) o);
            }
        });
    }
}
