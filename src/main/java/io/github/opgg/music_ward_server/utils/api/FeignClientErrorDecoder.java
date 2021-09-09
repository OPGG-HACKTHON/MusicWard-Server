package io.github.opgg.music_ward_server.utils.api;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.opgg.music_ward_server.exception.InvalidTokenException;
import io.github.opgg.music_ward_server.exception.RequestFailToOtherServerException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

    	if(response.status() == 401)
    		throw new InvalidTokenException();
        if(response.status() >= 400)
            throw new RequestFailToOtherServerException();

        return FeignException.FeignClientException.errorStatus(methodKey, response);
    }

}
