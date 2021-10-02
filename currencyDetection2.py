import tensorflow as tf

import os
import cv2
import numpy as np
import matplotlib.pyplot as plt
from tqdm import tqdm
from tensorflow.keras.applications.mobilenet import preprocess_input
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from sklearn.metrics import classification_report, confusion_matrix

IMAGE_SIZE = 128
BATCH_SIZE = 8




# train_datagen = ImageDataGenerator(rescale = 1./255,
#                                    shear_range = 0.2,
#                                    zoom_range = 0.2,
#                                    horizontal_flip = True)
# training_set = train_datagen.flow_from_directory('D:\DATASETS\PKrs_currency-master\Pkrs_currency_dataset_train_valid\\train',
#                                                  target_size = (512, 512),
#                                                  batch_size = 32,
#                                                  class_mode = 'categorical',
#                                                  shuffle = False)

# # Preprocessing the Test set
# test_datagen = ImageDataGenerator(rescale = 1./255)
# test_set = test_datagen.flow_from_directory('D:\DATASETS\PKrs_currency-master\Pkrs_currency_dataset_train_valid\\valid',
#                                             target_size = (512, 512),
#                                             batch_size = 32,
#                                             class_mode = 'categorical',
#                                             shuffle = False)






train_datagen = ImageDataGenerator(
    preprocessing_function = preprocess_input,
    rotation_range = 180,
    brightness_range=[0.8, 1.2],
    width_shift_range=0.1,
    height_shift_range=0.1,
    fill_mode = 'constant',
    )

test_datagen = ImageDataGenerator(preprocessing_function = preprocess_input)

train_generator = train_datagen.flow_from_directory(
    'D:\DATASETS\PKrs_currency-master\Pkrs_currency_dataset_train_valid\\train',
    classes = ['10', '20', '50', '100', '500', '1000'],
    target_size = (IMAGE_SIZE, IMAGE_SIZE),
    seed = 10,
    class_mode='categorical',
    batch_size = BATCH_SIZE)

test_set = test_datagen.flow_from_directory(
    'D:\DATASETS\PKrs_currency-master\Pkrs_currency_dataset_train_valid\\valid',
    classes = ['10','20', '50', '100', '500', '1000'],
    target_size = (IMAGE_SIZE, IMAGE_SIZE),
    batch_size = BATCH_SIZE,
    class_mode='categorical',
    seed = 3)

# test_generator = test_datagen.flow_from_directory(path_test_cropped,
#                                                   classes = ['none', '5', '10', '20', '50', '100', '200', '500', '2000'],
#                                                   target_size=(IMAGE_SIZE, IMAGE_SIZE),
#                                                   batch_size=BATCH_SIZE)


IMG_SHAPE = (IMAGE_SIZE, IMAGE_SIZE, 3)

base_model = tf.keras.applications.MobileNetV2(input_shape=IMG_SHAPE,
                                              include_top=False,
                                              weights= 'imagenet')

model = tf.keras.Sequential([
  base_model,
  tf.keras.layers.GlobalMaxPooling2D(),
  tf.keras.layers.Flatten(),
  tf.keras.layers.BatchNormalization(),
  tf.keras.layers.Dropout(0.5),
  tf.keras.layers.Dense(512),
  tf.keras.layers.Activation('relu'),
  tf.keras.layers.BatchNormalization(),
  tf.keras.layers.Dropout(0.5),
  tf.keras.layers.Dense(6, activation='softmax')
])



# base_model.trainable = False


model.compile(optimizer='adam',
              loss='categorical_crossentropy',
              metrics = ['accuracy'])



model.summary()




history = model.fit(x= train_generator,
                    epochs=20,
                    validation_data=test_set)

model.save('model')