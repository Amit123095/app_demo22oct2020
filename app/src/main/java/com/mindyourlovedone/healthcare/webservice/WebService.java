package com.mindyourlovedone.healthcare.webservice;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by welcome on 11/17/2017.
 */

public class WebService {

    //AWS Test Server
  /*  private final static String POST_PDF_URL = "http://18.188.114.139/public/test/public/webservices/fax/sendFax";
    private final String CREATE_PROFILE_URL = "http://18.188.114.139/public/test/public/webservices/user/createProfile";
    private final String GET_PROFILE_URL = "http://18.188.114.139/public/test/public/webservices/user/getProfile";
    private final String LOGIN_PROFILE_URL = "http://18.188.114.139/public/test/public/webservices/user/loginUser";
    private final String EDIT_PROFILE_URL = "http://18.188.114.139/public/test/public/webservices/user/editProfile";
    private final String UNSSUBSCRIBE_ME_URL = "http://18.188.114.139/public/test/public/webservices/user/unRegister";*/

    //AWS Live Server
  /*   private final static String POST_PDF_URL = "http://18.188.114.139/webservices/fax/sendFax";
    private final String CREATE_PROFILE_URL = "http://18.188.114.139/webservices/user/createProfile";
    private final String GET_PROFILE_URL = "http://18.188.114.139/webservices/user/getProfile";
    private final String LOGIN_PROFILE_URL = "http://18.188.114.139/webservices/user/loginUser";
    private final String EDIT_PROFILE_URL = "http://18.188.114.139/webservices/user/editProfile";
    private final String UNSSUBSCRIBE_ME_URL = "http://18.188.114.139/webservices/user/unRegister";
    */

    //Live Server
//    private final static String Base_URL ="http://mindyour-lovedones.com/MYLO/index.php/webservices"; //OLD Production
    private final static String Base_URL = "http://18.188.114.139/public/test/public/webservices"; //test -Nikita#Sub
    private final static String POST_PDF_URL = Base_URL + "/fax/sendFax";
    private final String CREATE_PROFILE_URL = Base_URL + "/user/createProfile";
    private final String GET_PROFILE_URL = Base_URL + "/user/getProfile";
    private final String LOGIN_PROFILE_URL = Base_URL + "/user/loginUser";
    private final String EDIT_PROFILE_URL = Base_URL + "/user/editProfile";
    private final String UNSSUBSCRIBE_ME_URL = Base_URL + "/user/unRegister";
    private final String POST_SUBSCRIPTION = Base_URL + "/user/postSubscription";//Nikita#Sub
    private final String GET_SUBSCRIPTION = Base_URL + "/user/getSubscription";//Nikita#Sub

//    //PROD
//    private final String PRIVACY_URL = "http://18.188.114.139/PRIVACY_POLICY.pdf";
//    private final String EULA_URL = "http://18.188.114.139/LICENSE_AGREEMENT.pdf";
//    private final String WALLET_URL = "http://18.188.114.139/Wallet_Card.pdf";

    //TEST
    public static String PRIVACY_URL = "http://18.188.114.139/test/public/PRIVACY_POLICY.pdf";
    public static String EULA_URL = "http://18.188.114.139/test/public/LICENSE_AGREEMENT.pdf";
    public static String WALLET_URL = "http://18.188.114.139/test/public/Wallet_Card.pdf";


//Test Server
   /* private final static String POST_PDF_URL = "http://demo.arihantwebconsultancy.com/mylo/public/webservices/fax/sendFax";
    private final String CREATE_PROFILE_URL = "http://demo.arihantwebconsultancy.com/mylo/public/webservices/user/createProfile";
    private final String GET_PROFILE_URL = "http://demo.arihantwebconsultancy.com/mylo/public/webservices/user/getProfile";
    private final String LOGIN_PROFILE_URL = "http://demo.arihantwebconsultancy.com/mylo/public/webservices/user/loginUser";
    private final String EDIT_PROFILE_URL = "http://demo.arihantwebconsultancy.com/mylo/public/webservices/user/editProfile";
    private final String UNSSUBSCRIBE_ME_URL = "http://demo.arihantwebconsultancy.com/mylo/public/webservices/user/unRegister";*/

    public static String uploadFile(String sourceFileUri, String number, String to, String from, String subject, String replayEmail, Context context) {
        String fileName = sourceFileUri;
        String result = "";
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        try {

            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(POST_PDF_URL);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("fromname", from);
            conn.setRequestProperty("subject", subject);
            conn.setRequestProperty("toname", to);

            //Shradha
            conn.setRequestProperty("replyemail", replayEmail);

            conn.setRequestProperty("faxnumber", number);


            conn.setRequestProperty("uploadFile", fileName);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=uploadFile;filename=\""
                    + fileName + "\"" + lineEnd);

            dos.writeBytes(lineEnd);

            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            int serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

//			Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage
//					+ ": " + serverResponseCode);

//			if (serverResponseCode == 200) {
//				Toast.makeText(context, "Fax Submitted Sucessfully", Toast.LENGTH_LONG).show();
//				System.out.println("" + serverResponseCode);
//			}

            // close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

            InputStream response = conn.getInputStream();


            result = decodeResponse(response);
            Log.i("uploadFile", "HTTP Response is : " + result);


        } catch (MalformedURLException ex) {

            ex.printStackTrace();

            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {

            e.printStackTrace();

            Log.e("Upload fileException",
                    "Exception : " + e.getMessage(), e);
        }
        return result;
    }

    public static String decodeResponse(InputStream is) {

        String result = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            result = sb.toString();

            Log.e("Response**** WebService", result);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return "exception";

        }

        String res = decodeStringBase64(result);
        return res;
    }

    public static String decodeStringBase64(String result) {

        String res;
        try {
            res = new String(Base64.decode(result, Base64.DEFAULT),
                    "ISO-8859-5");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Response**** WebService", "String decode Exception");

            return "exception";
        }

        Log.e("Unsbcribe", res + "");

        return res;

    }

    //    Nikita#Sub
    public String postSubscriptionData(String userid,
                                       String transactionId, String startDate, String endDate, String email) {

        // new changes - nikita
        HttpURLConnection conn = null;
        String result = "";
        InputStream is = null;
        try {
            URL url = new URL(POST_SUBSCRIPTION);

            conn = (HttpURLConnection) url.openConnection();
            Log.e("URL parameter", "userid :" + userid + "\ntransactionId : "
                    + transactionId + " \nstartDate : " + startDate + " \nendDate :" + endDate
                    + "\nsource :" + "Android");

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            //conn.setRequestProperty("userid", userid);
            conn.setRequestProperty("email", email);// Varsha 15 oct 2019
            conn.setRequestProperty("transactionId", transactionId);
            conn.setRequestProperty("startDate", startDate);
            conn.setRequestProperty("endDate", endDate);
            conn.setRequestProperty("source", "Android");

            conn.connect();

            // get stream
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

        } catch (ClientProtocolException e) {
            return "exception";
        } catch (IOException e) {
            return "exception";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        result = decodeResponse(is);

        return result;

    }

    //    Nikita#Sub
    public String getSubscriptionData(String userid, String email) {

        // new changes - nikita
        HttpURLConnection conn = null;
        String result = "";
        InputStream is = null;
        try {
            URL url = new URL(GET_SUBSCRIPTION);

            conn = (HttpURLConnection) url.openConnection();
            Log.e("URL parameter", "userid :" + userid);

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
           // conn.setRequestProperty("userid", userid);
            conn.setRequestProperty("email", email);// Varsha 15 oct 2019

            conn.connect();

            // get stream
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

        } catch (ClientProtocolException e) {
            return "exception";
        } catch (IOException e) {
            return "exception";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        result = decodeResponse(is);

        return result;

    }


    public String createProfile(String firstName, String lastName,
                                String state, String mail, String password, String deviceUdid,
                                String deviceType) {

//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(CREATE_PROFILE_URL);

        // new changes - nikita
        HttpURLConnection conn = null;
        String result = "";
        InputStream is = null;
        try {
            URL url = new URL(CREATE_PROFILE_URL);

            conn = (HttpURLConnection) url.openConnection();
            Log.e("URL parameter", "First Name :" + firstName + "\nlastName : "
                    + lastName + " \nState : " + state + " \nemail :" + mail
                    + "\npassword :" + password + " \nDeviceId :" + deviceUdid
                    + " \ndeviceType :" + deviceType);

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("firstName", firstName);
            conn.setRequestProperty("lastName", lastName);
            conn.setRequestProperty("state", state);
            conn.setRequestProperty("email", mail.trim());
            conn.setRequestProperty("password", password);
            conn.setRequestProperty("deviceUdid", deviceUdid);
            conn.setRequestProperty("deviceType", deviceType);

//            Uri.Builder builder = new Uri.Builder()
//                    .appendQueryParameter("firstName", firstName)
//                    .appendQueryParameter("lastName", lastName)
//                    .appendQueryParameter("state", state)
//            .appendQueryParameter("email", mail.trim())
//            .appendQueryParameter("password", password)
//            .appendQueryParameter("deviceUdid", deviceUdid)
//                    .appendQueryParameter("deviceType", deviceType);
//            String query = builder.build().getEncodedQuery();
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(query);
//            writer.flush();
//            writer.close();
//            os.close();

            conn.connect();

            // get stream
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }


//            httppost.setHeader("firstName", firstName);
//            httppost.setHeader("lastName", lastName);
//            httppost.setHeader("state", state);
//            httppost.setHeader("email", mail.trim());
//            httppost.setHeader("password", password);
//            httppost.setHeader("deviceUdid", deviceUdid);
//            httppost.setHeader("deviceType", deviceType);
//            HttpResponse response = httpclient.execute(httppost);
//
//            HttpEntity responseEntity = response.getEntity();
//
//            if (responseEntity != null) {
//
//                is = responseEntity.getContent();
//            }

        } catch (ClientProtocolException e) {
            return "exception";
        } catch (IOException e) {
            return "exception";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        result = decodeResponse(is);

        return result;

    }

    public String getProfile(String name, String email) {
        // new changes - nikita

        HttpURLConnection conn = null;
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(LOGIN_PROFILE_URL);
        String result = "";
        InputStream is = null;
        try {
            URL url = new URL(LOGIN_PROFILE_URL);

            conn = (HttpURLConnection) url.openConnection();
            Log.e("Encode String", name);
            Log.e("Encode String", email);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("firstName", name);
            conn.setRequestProperty("email", email);

//            Uri.Builder builder = new Uri.Builder()
//                    .appendQueryParameter("firstName", name)
//                    .appendQueryParameter("email", email);
//            String query = builder.build().getEncodedQuery();
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(query);
//            writer.flush();
//            writer.close();
//            os.close();

            conn.connect();

            // get stream
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

//            httppost.setHeader("firstName", name);
//            httppost.setHeader("email", email);
//
//            HttpResponse response = httpclient.execute(httppost);
//
//            HttpEntity responseEntity = response.getEntity();
//
//            if (responseEntity != null) {
//
//                is = responseEntity.getContent();
//            }

        } catch (ClientProtocolException e) {
            return "exception";
        } catch (IOException e) {
            return "exception";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }


        result = decodeResponse(is);

        return result;

    }

    /* public String unSubscribeMe(String userId) {

         HttpClient httpclient = new DefaultHttpClient();
         HttpPost httppost = new HttpPost(UNSSUBSCRIBE_ME_URL);
         String result = "";
         InputStream is = null;
         try {

             Log.e("Encode String", userId);

             httppost.setHeader("userId", userId);

             HttpResponse response = httpclient.execute(httppost);

             HttpEntity responseEntity = response.getEntity();

             if (responseEntity != null) {

                 is = responseEntity.getContent();
             }

         } catch (ClientProtocolException e) {
             return "exception";
         } catch (IOException e) {
             return "exception";
         }

         result = decodeResponse(is);

         return result;

     }
 */
    public String editProfile(String id, String firstName, String lastName,
                              String state, String email, String password) {
        // new changes - nikita

        HttpURLConnection conn = null;
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(EDIT_PROFILE_URL);
        String result = "";
        InputStream is = null;
        try {
            URL url = new URL(EDIT_PROFILE_URL);

            conn = (HttpURLConnection) url.openConnection();
            Log.e("URL parameter", "id :" + id + "First Name :" + firstName
                    + "\nlastName : " + lastName + " \nState : " + state
                    + " \nemail :" + email + "\npassword :" + password);

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("userId", id);
            conn.setRequestProperty("firstName", firstName);
            conn.setRequestProperty("lastName", lastName);
            conn.setRequestProperty("state", state);
            conn.setRequestProperty("email", email.trim());
            conn.setRequestProperty("password", password);

//            Uri.Builder builder = new Uri.Builder()
//                    .appendQueryParameter("userId", id)
//                    .appendQueryParameter("firstName", firstName)
//                    .appendQueryParameter("lastName", lastName)
//                    .appendQueryParameter("state", state)
//                    .appendQueryParameter("email", email.trim())
//                    .appendQueryParameter("password", password);
//            String query = builder.build().getEncodedQuery();
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(query);
//            writer.flush();
//            writer.close();
//            os.close();

            conn.connect();

            // get stream
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }


//            httppost.setHeader("userId", id);
//            httppost.setHeader("firstName", firstName);
//            httppost.setHeader("lastName", lastName);
//            httppost.setHeader("state", state);
//            httppost.setHeader("email", email.trim());
//            //    if (!password.equals("")) {
//            httppost.setHeader("password", password);
//            //   }
//            HttpResponse response = httpclient.execute(httppost);
//
//            HttpEntity responseEntity = response.getEntity();
//
//            if (responseEntity != null) {
//
//                is = responseEntity.getContent();
//            }

        } catch (ClientProtocolException e) {
            return "exception";
        } catch (IOException e) {
            return "exception";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }


        result = decodeResponse(is);

        return result;

    }
}
