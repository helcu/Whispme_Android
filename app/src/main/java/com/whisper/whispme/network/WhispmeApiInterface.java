package com.whisper.whispme.network;

import com.whisper.whispme.models.User;
import com.whisper.whispme.models.Whisp;

import java.util.List;

/**
 * Created by NightmareTK on 02/07/2017.
 */

public class WhispmeApiInterface {

    public interface SignInListener {
        void onEventCompleted(int userId);

        void onEventFailed(String apiResponse);
    }

    public interface GetNearWhispsListener {
        void onEventCompleted(List<Whisp> whisps);

        void onEventFailed(String apiResponse);
    }

    public interface UploadWhispListener {
        void onEventCompleted(String apiResponse);

        void onEventFailed(String apiResponse);
    }

    public interface GetWhispDetailListener {
        void onEventCompleted(Whisp whisp);

        void onEventFailed(String apiResponse);
    }

    public interface SignUpListener {
        void onEventCompleted(boolean wasCreated);

        void onEventFailed(String apiResponse);
    }

    public interface GetUserDetailListener {
        void onEventCompleted(User user);

        void onEventFailed(String apiResponse);
    }
}
