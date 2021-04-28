tools={}

function tools.getRandomStr(len)
	local rankStr=""
	local randNum=0
	for i=0,len do
		if math.random(1,3)==1 then
			randNum=String.char(math.random(0,26)+65)
		elseif math.random(1,3)==2 then
			randNum=String.char(math.random(0,26)+97)
		else
			randNum=math.random(0,10)
		end
		rankStr=rankStr..randNum
	end
	return rankStr
	-- body
end

function tools.getDevice()
	local headers=ngx.req.get_headers()
	local userAgent=headers["User-Agent"]
	local mobile={
		"iphone","android","touch","ipad","symbian","htc","palmos","blackberry","operamini","windows ce","nokia","fennec","macintosh","hiptop","kindle","mot"
	}
	userAgent=String.lower(userAgent)

	for i,v in ipairs(mobile) do
		if String.match(userAgent,v)then
			return v
		end
	end
	return userAgent
	-- body
end

function tools.getClientIp()
	local headers=ngx.req.get_headers()
	local ip=headers["X-REAL_IP"] or headers["X_FORWARDED_FOR"] or ngx.var.remote_addr or "0.0.0.0"
	return ip
	-- body
end

return tools