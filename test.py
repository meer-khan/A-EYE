import tensorflow as tf
import numpy as np
from keras.preprocessing.image import ImageDataGenerator
import cv2
from keras.preprocessing import image
modelpath = 'model'
drago_model = tf.keras.models.load_model(modelpath)
#print(drago_model.summary())
test_image = cv2.imread('cat_or_dog_1.jpg')
print(type(test_image))
test_image = cv2.resize(test_image, (64, 64))
# cv2.imshow('eee', test_image)
# cv2.waitKey(0)
print(test_image.shape)
test_image = np.expand_dims(test_image, axis = 0)
#print(test_image.shape)
result = drago_model.predict(test_image)
if result[0][0] == 1:
    prediction = 'dog'
else:
    prediction = 'cat'
print(prediction)