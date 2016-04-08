package vn.com.basc.bookingticket.network;

/**
 * Created by longnguyen on 14/08/2015.
 */
public interface IWSResponse {
    public void processWSFinish(String wsCallingCode, String output);
    public void processWSError(String wsCallingCode, String error);

    public void processFileFinish(String wsCallingCode, String output, Object sourceInfo);
    public void processFileError(String wsCallingCode, String error, Object sourceInfo);



}
