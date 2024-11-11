package com.canvas.command.page;

import com.canvas.common.page.PageRequest;
import com.canvas.common.page.Sort;

public class PageRequestFixture {
    public static PageRequest getPageRequest() {
        return new PageRequest(1, 9, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
