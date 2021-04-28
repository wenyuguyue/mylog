local cjson=require("cjson")
local producer=require("resty.kafka.producer")

local broker_list={
     {host="127.0.0.1",port=9092},
}

local log_json={}
log_json["message"]="this is a test"
log_json["from"]="nginx"
log_json["rid"]="a"
log_json["sid"]="b"
log_json["tid"]="c"

local message=cjson.encode(log_json);

local bp=producer:new(broker_list,{producer_type="async"})
local ok,err=bp:send("logs",nil,message)

if not ok then
    ngx.log(ngx.ERR,"kafka send err:",err)
    return
end