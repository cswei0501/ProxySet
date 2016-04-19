#!/system/bin/sh

# baidu.com
# iptables -I OUTPUT -p tcp -m string --string "baidu.com" --algo bm -j DROP


# uid
# iptables -t nat -I OUTPUT -p tcp -m owner --uid-owner 10120 -j DROP
case $1 in
start)
 case $2 in
  http)
   iptables -t nat -A OUTPUT -p tcp --dport 80 -j REDIRECT --to 8123
   iptables -t nat -A OUTPUT -p tcp --dport 443 -j REDIRECT --to 8124
   iptables -t nat -A OUTPUT -p tcp --dport 5228 -j REDIRECT --to 8124
   ;;
  socks)
   iptables -t nat -A OUTPUT -p tcp -j REDIRECT --to 8123
 esac
 ;;
stop)
 iptables -t nat -F OUTPUT
 ;;
esac
