package fun.xukun.platform.tool.web;

import fun.xukun.common.model.response.ResponseResult;
import fun.xukun.common.model.response.ResponseResultBuilder;
import fun.xukun.common.web.BaseController;
import fun.xukun.platform.tool.service.FileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日期:2020/8/3
 * 文件数据控制类
 *
 * @author xukun
 * @version 1.00
 */
@RestController
@RequestMapping("file")
@Api(value = "API - FileInfoController", tags = "文件模块接口详情")
@RequiredArgsConstructor
public class FileInfoController extends BaseController {

    private final FileInfoService fileInfoService;

    @ApiOperation(value = "文件下载接口", notes = "文件下载接口,支持POST方式", response = ResponseResult.class)
    @GetMapping("{id}")
    public ResponseResult<Void> download(@PathVariable String id) {
        fileInfoService.download(id, response);
        return ResponseResultBuilder.builder().success();
    }

}
