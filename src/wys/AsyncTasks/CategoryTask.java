package wys.AsyncTasks;

import java.util.ArrayList;
import java.util.List;

import wys.Business.BaseBusiness;
import wys.CustomInterfaces.OnGetCategoriesListener;
import wysApi.WysApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class CategoryTask extends BaseAsyncTaskManager {

	private OnGetCategoriesListener _onCategoriesListener;
	private Context _ctx;
    private ProgressDialog progressDialog;
    
	public CategoryTask(OnGetCategoriesListener oncCategoriesListener,
			Context context) {
		this._onCategoriesListener = oncCategoriesListener;
		this._ctx = context;
	}

	public void ExecuteGetCategories() {
		new CategoryAsync().execute();
	}

	private class CategoryAsync extends
			AsyncTask<Void, Void, ArrayList<BaseBusiness>> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Logging in...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<BaseBusiness> doInBackground(Void... params) {
			ArrayList<BaseBusiness> ArrayList = new WysApi().GetCategories();

			return ArrayList;
		}

		@Override
		protected void onPostExecute(ArrayList<BaseBusiness> result) {
			progressDialog.dismiss();
			if (result != null) {
               if(_onCategoriesListener !=null)
               {
            	   _onCategoriesListener.OnCategoriesReceived(result);
               }
			}

			super.onPostExecute(result);
		}

	}

}
