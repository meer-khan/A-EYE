import cv2
from pyzbar import pyzbar
# import speech_recognition as sr 
# from gtts import gTTS
# import os



cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()
    frame = cv2.resize(frame, (0, 0),fx=0.50, fy=0.50)
    cv2.putText(frame, "Press q to close camera", (10,10),cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 100), 1)	
# find the barcodes in the frame and decode each of the barcodes
    barcodes = pyzbar.decode(frame)
    print(barcodes)
	# loop over the detected barcodes
    for barcode in barcodes:
			# extract the bounding box location of the barcode and draw
			# the bounding box surrounding the barcode on the image
        (x, y, w, h) = barcode.rect
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 255), 1)
			# the barcode data is a bytes object so if we want to draw it
			# on our output image we need to convert it to a string first
        barcodeData = barcode.data.decode("utf-8")
        barcodeType = barcode.type	
        print(barcodeData)		
			# draw the barcode data and barcode type on the image
        text = "{} ({})".format(barcodeData, barcodeType)
        cv2.putText(frame, text, (x, y - 10),cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 1)
        #     # mytext = "hello world"
        # output = gTTS(text=text, lang='en', slow=False)

        # output.save("output.mp3")
        # os.system("start output.mp3")
        cv2.imshow("window",frame)
        if cv2.waitKey(1)==ord('q'):
            break
cap.release()
cv2.destroyAllWindows()





# cap = cv2.VideoCapture(0)
# cv2.namedWindow("Window")

# while True:
#     ret, frame = cap.read()
#     # frame = cv2.resize(frame, (0, 0),fx=0.50, fy=0.50)
#     cv2.putText(frame, "Press q to close camera", (10,10),cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 100), 1)	
#     # find the barcodes in the frame and decode each of the barcodes
#     barcodes = pyzbar.decode(frame)
#     # print(barcodes)
# 	# loop over the detected barcodes
#     for barcode in barcodes:
# 	# extract the bounding box location of the barcode and draw
# 	# the bounding box surrounding the barcode on the image
#         (x, y, w, h) = barcode.rect
#         cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 255), 1)
# 			# the barcode data is a bytes object so if we want to draw it
# 			# on our output image we need to convert it to a string first
#         barcodeData = barcode.data.decode("utf-8")
#         barcodeType = barcode.type			
# 			# draw the barcode data and barcode type on the image
#         text = "{} ({})".format(barcodeData, barcodeType)
#         cv2.putText(frame, text, (x, y - 10),cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 1)
#             # mytext = "hello world"q
#         # output = gTTS(text=text, lang='en', slow=False)
#         # output.save("output.mp3")
#         # os.system("start output.mp3")

#         cv2.imshow("Window",frame)
#         if cv2.waitKey(1) & 0xFF == ord('q'):
#             break
# cap.release()
# cv2.destroyAllWindows()
