package com.example.ilya.githubapi;


import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        findViewById(R.id.find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = (EditText)findViewById(R.id.username);
                find(input.getText().toString(), rv);
            }
        });


    }

    public void  find(String username, final RecyclerView rv){
        RepoFactory rf = new RepoFactory(username);
        Log.d("11", username);
        RepoFactory.user = username;
        final ListValidator validator = rf.retrofitRepo().create(ListValidator.class);

        final Call<List<RepoValidate>> call = validator.validate();

        NetworkThread.getInstance().execute(call, new NetworkThread.ExecuteCallback<List<RepoValidate>>() {

            @Override
            public void onSuccess(final List<RepoValidate> result) {
                rv.setAdapter(new RecyclerView.Adapter() {
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new ItemViewHolder(
                                getLayoutInflater().inflate(R.layout.repo, parent, false)
                        );
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                        for (RepoValidate temp : result) {
                            ((ItemViewHolder) holder).bind(temp);
                        }

                    }

                    @Override
                    public int getItemCount() {
                        return result.size();
                    }

                    @Override
                    public int getItemViewType(int position) {
                        return position == 1 ? 1 : 2;
                    }
                });
            }

            @Override
            public void onError(Exception ex) {
            }
        });
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView fullname;
        private final TextView rid;
        private final TextView html_url;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.html_url = (TextView) itemView.findViewById(R.id.html_url);
            this.rid = (TextView) itemView.findViewById(R.id.rid);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.fullname = (TextView) itemView.findViewById(R.id.fullname);
        }

        public void bind(RepoValidate item) {
            name.setText(item.name);
            rid.setText(String.valueOf(item.id));
            fullname.setText(item.full_name);
            html_url.setText(item.html_url);
        }

    }

}
