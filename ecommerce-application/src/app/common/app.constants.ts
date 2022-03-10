import { environment } from '../../environments/environment';
import { HttpHeaders } from '@angular/common/http';

export class AppConstants {
    static API_BASE_URL = environment.apiBaseUrl;
    private static OAUTH2_URL = AppConstants.API_BASE_URL + "oauth2/authorization/";
    private static REDIRECT_URL = environment.clientUrl;
    public static API_URL = AppConstants.API_BASE_URL + "api/";
    public static AUTH_API = AppConstants.API_URL + "auth/";
    public static MANAGEMENT_API = AppConstants.API_URL + "management/";
    public static IMAGE_API = AppConstants.MANAGEMENT_API + "images/";
    public static GOOGLE_AUTH_URL = AppConstants.OAUTH2_URL + "google" + AppConstants.REDIRECT_URL;
    public static FACEBOOK_AUTH_URL = AppConstants.OAUTH2_URL + "facebook" + AppConstants.REDIRECT_URL;
    public static httpOptions = {
      headers: new HttpHeaders({
        'Accept':'application/json',
        'Content-Type': 'application/json;charset=UTF-8;multipart/form-data'
      })
    };
}
