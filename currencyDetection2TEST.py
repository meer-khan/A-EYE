import tensorflow as tf
import numpy as np
from keras.preprocessing.image import ImageDataGenerator
import cv2
from keras.preprocessing import image

modelpath = 'model'
model = tf.keras.models.load_model(modelpath)
# print(model.summary())
test_image = cv2.imread('test4.jpeg')
test_image = cv2.resize(test_image, (128, 128))
print(test_image.shape)
test_image = np.expand_dims(test_image, axis = 0)
result = model.predict(test_image)
print(result)
# cv2.imshow('eee', test_image)
# cv2.waitKey(0)