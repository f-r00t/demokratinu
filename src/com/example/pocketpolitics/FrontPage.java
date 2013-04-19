package com.example.pocketpolitics;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import com.example.pocketpolitics.control.ArticleListAdapter;
import com.example.pocketpolitics.model.Article;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FrontPage extends Activity {


      private ListView listViewArticles;
      private Context ctx;
     
      @Override
      public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_front_page);
            ctx=this;
            
            
            //Replace this with the factory later, really ugly code incoming:
            List<Article> articleList = new ArrayList<Article>();
            Article a = new Article();
            a.setTitle("Artikel A");
            articleList.add(a);
            Article b = new Article();
            b.setTitle("Artikel B");
            articleList.add(b);
            Article c = new Article();
            c.setTitle("Artikel C");
            articleList.add(c);

            listViewArticles =  ( ListView ) findViewById( R.id.article_list);
            listViewArticles.setAdapter(new ArticleListAdapter(this, articleList));
      }
}
