package com.example.instabugtask.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.instabugtask.R
import com.example.instabugtask.ui.screens.main.model.RequestType

@Composable
fun CurlInputField(modifier: Modifier = Modifier, onUrlChange: (String) -> Unit, url: String) {
  Column(modifier = modifier.clip(RoundedCornerShape(10.dp)).background(Color.Transparent), verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {
    MyTextField(
      value = url,
      modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
      label = "Enter URL Here",
      placeholder = "https://example.com",
      onValueChanged = { onUrlChange(it) })
  }
}


@Composable
fun RequestChooseField(
  modifier: Modifier = Modifier,
  onClick: (String) -> Unit,
  requestType: RequestType
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceAround,
    verticalAlignment = Alignment.CenterVertically
  ) {
    SelectableButton(
      text = "GET",
      isSelected = requestType is RequestType.GetRequest,
      onClick = {
        onClick("get")
      })
    SelectableButton(
      text = "POST",
      isSelected = requestType is RequestType.PostRequest,
      onClick = { onClick("post") })

    SelectableButton(
      text = "POST WITH FILE",
      isSelected = requestType is RequestType.PostWithFileRequest,
      onClick = { onClick("post_with_file") })

  }
}


@Composable
fun AddHeaders(
  modifier: Modifier = Modifier,
  headerValue: String,
  headerKey: String,
  addedHeaders: Map<String, String>, // Added headers list
  onHeaderKeyChange: (String) -> Unit,
  onHeaderValueChanged: (String) -> Unit,
  onAddHeaderClick: () -> Unit
) {
  Column(modifier = modifier) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
      Column {
        MyTextField(
          label = "Header Key",
          value = headerKey,
          placeholder = "",
          onValueChanged = { onHeaderKeyChange(it) })
        MyTextField(
          value = headerValue,
          label = "Header Value",
          placeholder = "",
          onValueChanged = { onHeaderValueChanged(it) })

      }
      ActionButton(modifier = Modifier
        .align(Alignment.CenterVertically)
        .padding(start = 5.dp), text = "Add", onClick = { onAddHeaderClick() })
    }

    if (addedHeaders.isNotEmpty()) {
      Text("Added Headers:", color = Color.White,fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
      addedHeaders.forEach { (key, value) ->
        Text("$key: $value", modifier = Modifier.padding(start = 8.dp), color = Color.White)
      }
    }
  }
}

@Composable
fun AddRequestParameters(
  queryParam: String,
  queryValue: String,
  onQueryParameterChange: (String) -> Unit,
  onQueryValueChange: (String) -> Unit,
  onAddClick: () -> Unit,
  modifier: Modifier = Modifier,

  ) {
  Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
    Column {
      MyTextField(
        label = "Query Parameter",
        value = queryParam,
        placeholder = "",
        onValueChanged = { onQueryParameterChange(it) })
      MyTextField(
        value = queryValue,
        label = "Query Value",
        placeholder = "",
        onValueChanged = { onQueryValueChange(it) })

    }
    ActionButton(modifier = Modifier
      .align(Alignment.CenterVertically)
      .padding(start = 5.dp), text = "Add", onClick = { onAddClick() })
  }

}

@Composable
fun AddRequestBody(
  jsonKey: String,
  jsonValue: String,
  onJsonKeyChange: (String) -> Unit,
  onJsonValueChange: (String) -> Unit,
  onAddClick: () -> Unit,
  modifier: Modifier = Modifier,

  ) {
  Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
    Column {
      MyTextField(
        label = "Json Key",
        value = jsonKey,
        placeholder = "",
        onValueChanged = { onJsonKeyChange(it) })
      MyTextField(
        value = jsonValue,
        label = "Json Value",
        placeholder = "",
        onValueChanged = { onJsonValueChange(it) })

    }
    ActionButton(modifier = Modifier
      .align(Alignment.CenterVertically)
      .padding(start = 5.dp), text = "Add", onClick = { onAddClick() })
  }

}


@Composable
fun RunButton(modifier: Modifier = Modifier, onRunClick: () -> Unit, onClearClick: () -> Unit) {
  Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
    ActionButton(text = "Execute", onClick = { onRunClick() })
    ActionButton(text = "Clear", onClick = { onClearClick() })
  }

}


@Preview(showBackground = true)
@Composable
fun Preview() {

}
