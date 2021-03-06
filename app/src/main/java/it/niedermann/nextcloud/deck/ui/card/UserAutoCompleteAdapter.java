package it.niedermann.nextcloud.deck.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextcloud.android.sso.exceptions.NextcloudFilesAppAccountNotFoundException;
import com.nextcloud.android.sso.exceptions.NoCurrentAccountSelectedException;
import com.nextcloud.android.sso.helper.SingleAccountHelper;
import com.nextcloud.android.sso.model.SingleSignOnAccount;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.niedermann.nextcloud.deck.DeckLog;
import it.niedermann.nextcloud.deck.R;
import it.niedermann.nextcloud.deck.model.User;
import it.niedermann.nextcloud.deck.persistence.sync.SyncManager;
import it.niedermann.nextcloud.deck.util.ViewUtil;

public class UserAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<User> userList = new ArrayList<>();
    private SyncManager syncManager;
    private long accountId;
    private LifecycleOwner owner;

    public UserAutoCompleteAdapter(@NonNull LifecycleOwner owner, Context context, long accountId) {
        this.owner = owner;
        this.context = context;
        this.accountId = accountId;
        syncManager = new SyncManager(context,null);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO implement proper butterknife implementation
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_dropdown_item_singleline, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        try {
            SingleSignOnAccount account =  SingleAccountHelper.getCurrentSingleSignOnAccount(context);
            ViewUtil.addAvatar(
                    context,
                    holder.avatar,
                    account.url,
                    getItem(position).getUid()
            );
        } catch (NextcloudFilesAppAccountNotFoundException e) {
            DeckLog.logError(e);
        } catch (NoCurrentAccountSelectedException e) {
            DeckLog.logError(e);
        }

        holder.displayname.setText(getItem(position).getDisplayname());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            final FilterResults filterResults = new FilterResults();
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    ((Fragment)owner).getActivity().runOnUiThread(() -> {
                        LiveData<List<User>> userLiveData = syncManager.searchUserByUidOrDisplayName(accountId, constraint.toString());
                        Observer<List<User>> observer = new Observer<List<User>>() {
                            @Override
                            public void onChanged(List<User> users) {
                                userLiveData.removeObserver(this);
                                if (users != null) {
                                    filterResults.values = users;
                                    filterResults.count = users.size();
                                    publishResults(constraint, filterResults);
                                } else {
                                    filterResults.values = new ArrayList<>();
                                    filterResults.count = 0;
                                }
                            }
                        };
                        userLiveData.observe(owner, observer);
                    });
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    if (!userList.equals(results.values)) {
                        userList = (List<User>) results.values;
                    }
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
    }

    static class ViewHolder {
        @BindView(R.id.user_avatar) ImageView avatar;
        @BindView(R.id.user_displayname) TextView displayname;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
