package moolab.com.br.life4two.core;

import android.content.Context;

import com.nispok.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import moolab.com.br.life4two.R;
import moolab.com.br.life4two.parsecloud.ParseConfig;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucas on 29/03/15.
 */
public class Cupid {

    private final Context context;

    public static final int WAITING = 0;
    public static final int ACCEPTED = 1;
    public static final int REJECTED = 2;

    private ParseUser invitedBy;
    private ParseObject invitation;

    public ParseUser getInvitedBy() {
        return invitedBy;
    }

    public ParseObject getInvitation() {
        return invitation;
    }

    public void saveStatus(int status, SaveCallback callback) {
        getInvitation().put(ParseKeysMaster.STATUS, status);
        getInvitation().saveInBackground(callback);
    }

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

        user.fetchIfNeededInBackground(new GetCallback<ParseObject>() {

            @Override
            public void done(final ParseObject user, ParseException e) {

                // If you do not have a Boo
                if (user.getParseObject(ParseKeysMaster.BOO) == null) {

                    // Check your invites status
                    List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

                    ParseQuery querySentBy = ParseQuery.getQuery(ParseConfig.OBJECT_INVITE);
                    querySentBy.whereEqualTo(ParseKeysMaster.SENT_BY, user);
                    queries.add(querySentBy);

                    ParseQuery querySenTo = ParseQuery.getQuery(ParseConfig.OBJECT_INVITE);
                    querySenTo.whereEqualTo(ParseKeysMaster.SENT_TO_PHONE, ((ParseUser) user).getUsername());
                    queries.add(querySenTo);

                    ParseQuery query = ParseQuery.or(queries);
                    query.include(ParseKeysMaster.SENT_BY);
                    query.whereNotEqualTo(ParseKeysMaster.STATUS, REJECTED);
                    query.findInBackground(new FindCallback() {

                        @Override
                        public void done(List list, ParseException e) {

                            if (e != null) {
                                return;
                            }

                            checkStatus(list);
                        }

                        @Override
                        public void done(Object o, Throwable throwable) {

                            if (throwable != null) {
                                return;
                            }

                            checkStatus((List<ParseObject>) o);
                        }

                        public void checkStatus(List<ParseObject> list) {

                            // Nothing happens
                            if (list.isEmpty()) {
                                callback.isEmpty();
                                return;
                            }

                            for (ParseObject parseObject : list) {

                                // You has been invited
                                switch (parseObject.getInt(ParseConfig.STATUS)) {
                                    case WAITING:
                                        callback.isWaiting();
                                        return;
                                    case ACCEPTED:
                                        callback.isReady();
                                        return;
                                }

                                // Check if you was invited
                                if (parseObject.getString(ParseKeysMaster.SENT_TO_PHONE).equals(((ParseUser) user).getUsername())) {
                                    invitedBy = parseObject.getParseUser(ParseKeysMaster.SENT_BY);
                                    invitation = parseObject;
                                    callback.isInvited();
                                }
                            }
                        }
                    });
                } else {

                    // Ready for the game
                    callback.isReady();
                }
            }
        });

    }
}
