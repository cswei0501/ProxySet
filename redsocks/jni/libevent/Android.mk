LOCAL_PATH := $(call my-dir)




include $(CLEAR_VARS)

LIBEVENT_SOURCES = \
	event.c \
	evthread.c \
	buffer.c \
	bufferevent.c \
	bufferevent_sock.c \
	bufferevent_filter.c \
	bufferevent_pair.c \
	listener.c \
	bufferevent_ratelim.c \
	evmap.c	\
	log.c \
	evutil.c \
	evutil_rand.c \
	strlcpy.c \
	select.c \
	poll.c \
	epoll.c \
	signal.c

	
LOCAL_MODULE := event
LOCAL_SRC_FILES := $(LIBEVENT_SOURCES)
LOCAL_CFLAGS := -O2 -g 
LOCAL_CFLAGS +=	-I$(LOCAL_PATH)/include
LOCAL_CFLAGS +=	-I$(LOCAL_PATH)/compat
LOCAL_CFLAGS +=	-DHAVE_CONFIG_H -Wall -fno-strict-aliasing 

include $(BUILD_STATIC_LIBRARY)