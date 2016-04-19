
LOCAL_PATH := $(call my-dir)

BF_PIE := -pie -fPIE


include $(CLEAR_VARS)
REDSOCKS_SOURCES := \
	base.c \
	dnstc.c \
	http-connect.c \
	log.c \
	md5.c \
	socks5.c \
	base64.c \
	http-auth.c \
	http-relay.c \
	main.c \
	parser.c \
	redsocks.c \
	socks4.c \
	utils.c \
	debug.c
#	redudp.c \
	

LOCAL_STATIC_LIBRARIES := libevent

LOCAL_MODULE := redsocks
LOCAL_SRC_FILES := $(addprefix redsocks/, $(REDSOCKS_SOURCES))
LOCAL_CFLAGS := -std=c99 -O2 -g -I$(LOCAL_PATH)/redsocks \
	-I$(LOCAL_PATH)/libevent/include \
	-I$(LOCAL_PATH)/libevent
LOCAL_CFLAGS += -D_XOPEN_SOURCE=600 -D_BSD_SOURCE -D_DEFAULT_SOURCE -Wall
LOCAL_CFLAGS += $(BF_PIE)
LOCAL_LDFLAGS += $(BF_PIE)
include $(BUILD_EXECUTABLE)

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
	evmap.c \
	log.c \
	evutil.c \
	evutil_rand.c \
	strlcpy.c \
	select.c \
	poll.c \
	epoll.c \
	signal.c \
	http.c

LOCAL_MODULE := event
LOCAL_SRC_FILES := $(addprefix libevent/, $(LIBEVENT_SOURCES))
LOCAL_CFLAGS := -O2 -g -I$(LOCAL_PATH)/libevent \
	-I$(LOCAL_PATH)/libevent/include \
	-I$(LOCAL_PATH)/libevent/compat
LOCAL_CFLAGS +=	-DHAVE_CONFIG_H -Wall -fno-strict-aliasing
LOCAL_CFLAGS += $(BF_PIE)
LOCAL_LDFLAGS += $(BF_PIE)
include $(BUILD_STATIC_LIBRARY)

