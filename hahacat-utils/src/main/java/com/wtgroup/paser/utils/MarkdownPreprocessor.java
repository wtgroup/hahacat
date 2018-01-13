package com.wtgroup.paser.utils;

import com.wtgroup.entity.Markdown;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.List;

public interface MarkdownPreprocessor {


    public List<Markdown> preprocess(List<Markdown> markdowns, Charset charset, MarkdownSlicePreprocessor mdsp) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException;

    public List<Markdown> preprocess(List<Markdown> markdowns, Charset charset) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException;

}
