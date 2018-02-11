import thread, time
import ConfigParser
from dejavu import Dejavu
#from dejavu.recognize import FileRecognizer
from dejavu.recognize import MicrophoneRecognizer
from pyfcm import FCMNotification

WINDOW_SIZE = 3

config_settings = ConfigParser.ConfigParser()
config_settings.read("configuration.config")

config = {
     "database": {
         "host": config_settings.get("configuration","aws_db_host"),
         "user": config_settings.get("configuration","aws_db_user"),
         "passwd": config_settings.get("configuration","aws_db_pwd"),
         "db": config_settings.get("configuration","aws_db_name"),
     },
     "database_type" : "mysql",
     "fingerprint_limit" : 10
}

djv = Dejavu(config)
djv.fingerprint_directory("audio_merged", [".mp3",".wav"])

#result = djv.recognize(FileRecognizer, "train_audio/door_bell_1.mp3")

def record_and_match(djv_copy):
    match = 0
    result = djv_copy.recognize(MicrophoneRecognizer, seconds=WINDOW_SIZE)
    print(result)
    if result is not None:
        if result['confidence'] > 3:
            print("This is matched"+result["song_name"])
            match = 1
        elif result["offset_seconds"] > 0.5 and result["offset"] > 10:
            print("This is matched"+result["song_name"])
            match = 1
        else:
            print("Not a match")
        if match = 1:
            push_service = FCMNotification(api_key="<api-key>")
            registration_id = "<device registration_id>"
            message_title = "Alert! Event Recognized."
            message_body = st.split("_")[0]+"_"+st.split("_")[1]
            result = push_service.notify_single_device(registration_id=registration_id, message_title=message_title, message_body=message_body)

while(1):
    thread.start_new_thread( record_and_match, (djv, ) )
    #thread.record_and_match( print_time, (djv, ) )
    time.sleep(5)
