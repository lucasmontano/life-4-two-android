package moolab.com.br.life4two.core;

import android.content.Context;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import moolab.com.br.life4two.core.model.BetOptionModel;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucasmontano on 21/04/15.
 */
public class BetOptions {

    private final Context context;

    public interface BetCallback {

        public void onFetchBetOptions(List<BetOptionModel> data);
    }

    public BetOptions(Context context) {
        this.context = context;
    }

    public void fetchBetOptions(final BetCallback betCallback) {

        ParseQuery query = ParseQuery.getQuery(ParseKeysMaster.OBJECT_BET_OPTIONS);
        query.orderByAscending(ParseKeysMaster.NAME);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
                betCallback.onFetchBetOptions(list);
            }

            @Override
            public void done(Object o, Throwable throwable) {
                betCallback.onFetchBetOptions((List<BetOptionModel>) o);
            }
        });
    }
}
