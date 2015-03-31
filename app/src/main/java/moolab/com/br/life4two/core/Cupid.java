package moolab.com.br.life4two.core;

import android.content.Context;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import moolab.com.br.life4two.parsecloud.ParseConfig;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucas on 29/03/15.
 */
public class Cupid {

    private final Context context;

    public interface CupidStatus {

        public void isInvited();
        public void isEmpty();
        public void isWaiting();
        public void isReady();
    }

    public Cupid(Context context) {
        this.context = context;
    }

    public void getStatus(ParseUser user, final CupidStatus callback) {

        if (user.getParseObject(ParseKeysMaster.BOO) == null) {

            if (user.getNumber(ParseKeysMaster.BOO_PHONE) == null) {

                Parse.initialize(context, ParseConfig.APP_ID, ParseConfig.CLIENT_KEY);
                ParseQuery query = ParseQuery.getQuery("_User");
                query.whereEqualTo(ParseKeysMaster.BOO_PHONE, user.getUsername());
                query.getFirstInBackground(new GetCallback() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {

                    }

                    @Override
                    public void done(Object o, Throwable throwable) {
                        if (o == null) {
                            callback.isEmpty();
                        } else {
                            callback.isInvited();
                        }
                    }
                });

            } else {
                callback.isWaiting();
            }
        } else {
            callback.isReady();
        }
    }
}
