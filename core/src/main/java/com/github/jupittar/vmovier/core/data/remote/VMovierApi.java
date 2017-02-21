package com.github.jupittar.vmovier.core.data.remote;

import com.github.jupittar.vmovier.core.data.entity.Banner;
import com.github.jupittar.vmovier.core.data.entity.Movie;
import com.github.jupittar.vmovier.core.data.entity.RawResponse;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface VMovierApi {

  @FormUrlEncoded
  @POST("apiv3/post/getPostByTab")
  Observable<RawResponse<List<Movie>>> getMoviesByTab(
      @Field("p") int pageIndex,
      @Field("size") int size,
      @Field("tab") String tab
  );

  @POST("apiv3/index/getBanner")
  Observable<RawResponse<List<Banner>>> getBanner();

}
