import thread, time
import ConfigParser
from dejavu import Dejavu
#from dejavu.recognize import FileRecognizer
from dejavu.recognize import MicrophoneRecognizer
from pyfcm import FCMNotification
import subprocess as sbp

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
        if match == 1:
            push_service = FCMNotification(api_key="AAAAOZz1_28:APA91bETO_8C5y6TffjdXDVNVJi3J0koJF2EDxJ38WxRh1n3OpFQFfQ_cE3IYceq5nJS_hs2U6gb-G328aGGSvzvdOvR6gMHfyuLbBeHIcoWrLbFHO9jQ3_iN4UWYzWzOx6KFrRGmA_F")
            registration_id = "f3GZVJlUplE:APA91bFeIxGox0A9RD1fhr_8zCJTfXBGIZLWKmWUkayE3gjKlMnEUiE2wbcwVPKa_bCBuzf0jyMZkCrfCsfypE1SgXY75LXDgbj0J5lLYcpCUJnXDpfy23NQzQ7A32HQHP6_2PTdFg8R"
            message_title = "Alert! Event Recognized."
            message_body = result["song_name"].split("_")[0]+"_"+result["song_name"].split("_")[1]
            result = push_service.notify_single_device(registration_id=registration_id, message_title=message_title, message_body=message_body)
            sbp.call(['notify-send',message_title,message_body])

while(1):
    thread.start_new_thread( record_and_match, (djv, ) )
    #thread.record_and_match( print_time, (djv, ) )
    time.sleep(5)
