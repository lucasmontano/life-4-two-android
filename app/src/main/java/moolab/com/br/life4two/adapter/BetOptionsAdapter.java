package moolab.com.br.life4two.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import moolab.com.br.life4two.R;
import moolab.com.br.life4two.core.model.BetOptionModel;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucasmontano on 21/04/15.
 */
public class BetOptionsAdapter extends RecyclerView.Adapter<BetOptionsAdapter.ViewHolder> {

    private SparseArray<BetOptionModel> selecteds = new SparseArray<BetOptionModel>();

    private final Context context;
    private List<BetOptionModel> data = new ArrayList<BetOptionModel>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View v;

        @InjectView(R.id.bet_name)
        public TextView mName;

        @InjectView(R.id.bet_point)
        public TextView mPoint;

        @InjectView(R.id.bet_selected)
        public Switch mSelected;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
            this.v = v;
        }
    }

    public BetOptionsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BetOptionModel> data) {
        this.data = data;
    }

    @Override
    public BetOptionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bet_option, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mName.setText(data.get(position).getString(ParseKeysMaster.NAME));

        StringBuilder points = new StringBuilder();
        points.append(data.get(position).getNumber(ParseKeysMaster.POINT));
        points.append(" ");
        points.append(context.getString(R.string.points));

        holder.mPoint.setText(points.toString());

        holder.mSelected.setChecked(selecteds.valueAt(position) != null);

        holder.mSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selecteds.append(position, data.get(position));
                } else {
                    selecteds.remove(position);
                }
            }
        });

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mSelected.setChecked( ! holder.mSelected.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<BetOptionModel> getSelecteds() {
        if (selecteds == null) return null;
        List<BetOptionModel> arrayList = new ArrayList<BetOptionModel>(selecteds.size());
        for (int i = 0; i < selecteds.size(); i++)
            arrayList.add(selecteds.valueAt(i));
        return arrayList;
    }
}
