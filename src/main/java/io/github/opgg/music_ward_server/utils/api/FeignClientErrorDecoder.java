package io.github.opgg.music_ward_server.utils.api;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(final String methodKey, Response response) {

        return FeignException.FeignClientException.errorStatus(methodKey, response);
    }

}
