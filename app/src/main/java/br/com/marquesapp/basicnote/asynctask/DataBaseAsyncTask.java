package br.com.marquesapp.basicnote.asynctask;

import android.os.AsyncTask;

public class DataBaseAsyncTask<T> extends AsyncTask<Void, Void, T> {

    private ExecuteListener<T> execute;
    private ResultDataListerner<T> resultData;

    public DataBaseAsyncTask(ExecuteListener<T> execute, ResultDataListerner<T> resultData) {
        this.execute = execute;
        this.resultData = resultData;
    }

    @Override
    protected T doInBackground(Void... voids) {
        return execute.action();
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        resultData.result(result);
    }

    public interface ExecuteListener<T>{
        T action();
    }

    public interface ResultDataListerner<T>{
        void result(T result);
    }
}
