package com.example.instabugtask.ui.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instabugtask.R
import com.example.instabugtask.domain.model.ResponseInfo
import com.example.instabugtask.utils.dimensions.LocalSpacing
import com.example.instabugtask.utils.fontfamilys.fontsfamilys

@Composable
fun BoldTextField(
  value: String,
  size: TextUnit,
  modifier: Modifier = Modifier,
  textAlign: TextAlign,
  color: Color? = null
) {
  Text(
    text = value,
    modifier = modifier
      .heightIn(),
    fontFamily = fontsfamilys.poppinsFamily,
    fontStyle = FontStyle.Normal,
    fontSize = size,
    overflow = TextOverflow.Clip,
    maxLines = 1,
    fontWeight = FontWeight.Bold,
    color = colorResource(id = R.color.black),
    textAlign = textAlign,

  )
}

@Composable
fun BasicButton(value: String, onClick: () -> (Unit), modifier: Modifier = Modifier) {
  Button(modifier = modifier
    .heightIn(),
    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.mainColor)),
    shape = RoundedCornerShape(5.dp),
    onClick = { onClick() }) {
    BoldTextField(value = value, size = 12.sp, textAlign = TextAlign.Center)

  }
}

@Composable
fun BasicTextField(value: String, modifier: Modifier = Modifier) {
  Text(
    text = value,
    modifier = modifier
      .fillMaxWidth()
      .heightIn(min = 70.dp),
    fontFamily = fontsfamilys.poppinsFamily,
    fontStyle = FontStyle.Normal,
    fontWeight = FontWeight(200),
    color = colorResource(id = R.color.black),
    textAlign = TextAlign.Center
  )
}

@Composable
fun MyTextField(
  value: String,
  label: String,
  placeholder: String,
  onValueChanged: (String) -> Unit,
  modifier: Modifier = Modifier
) {

  OutlinedTextField(
    value = value,
    modifier = modifier,
    shape = RoundedCornerShape(10.dp),
    onValueChange = {
      onValueChanged(it)
    },
    label = {
      Text(
        text = label, style = TextStyle(
          fontFamily = fontsfamilys.poppinsFamily,
          fontWeight = FontWeight(800),
          color = colorResource(id = R.color.labelColor),
          textAlign = TextAlign.Center,
        )
      )
    },
    placeholder = {
      Text(
        text = placeholder, style = TextStyle(
          fontFamily = fontsfamilys.poppinsFamily,
          fontWeight = FontWeight(200),
          color = colorResource(id = R.color.placeholderColor),
          textAlign = TextAlign.Center,
        )
      )
    },
    textStyle = TextStyle(
      color = Color.White
    ),
    keyboardOptions = KeyboardOptions.Default

  )
}

@Composable
fun ActionButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  BasicButton(modifier = modifier, value = text, onClick = { onClick() })
}

@Composable
fun SelectableButton(
  text: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  selectedColor: Color = colorResource(id = R.color.mainColor),
  unselectedColor: Color = Color.White,
) {
  val backgroundColor by animateColorAsState(
    targetValue = if (isSelected) selectedColor else unselectedColor,
    animationSpec = tween(durationMillis = 300), label = ""
  )

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(50.dp))
      .border(
        width = 2.dp,
        color = if (isSelected) Color.Black else colorResource(id = R.color.mainColor),
        shape = RoundedCornerShape(50.dp)
      )
      .widthIn(110.dp)
      .background(
        color = backgroundColor,
      )
      .clickable { onClick() }
      .padding(8.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = text,
      color = Color.Black,
      fontSize = 12.sp,
      textAlign = TextAlign.Center,
      fontWeight = FontWeight.Bold
    )
  }
}

@Composable
fun ExpandableCard(responseInfo: ResponseInfo?,onFilePathClicked : (String)-> Unit ,modifier: Modifier = Modifier) {
  var cardExpandedState by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .clickable { cardExpandedState = !cardExpandedState }
      .fillMaxWidth()
      .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
      .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
      .padding(15.dp) ,
  ) {
    BoldTextField(
      value = if (responseInfo?.requestUrl != null) "${responseInfo.requestUrl}" else stringResource(
        R.string.request_details
      ),
      size = 20.sp,
      color = Color.Black,
      textAlign = TextAlign.Start,



    )
    // Header
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {


      IconButton(
        onClick = { cardExpandedState = !cardExpandedState },
        modifier = Modifier.align(Alignment.CenterVertically)
      ) {
        Icon(
          imageVector = if (cardExpandedState) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
          contentDescription = "Expand/Collapse"
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    AnimatedVisibility(
      visible = cardExpandedState,
      enter = expandVertically(),
      exit = shrinkVertically()
    ) {
      // Add your fields here
      val fields = listOf(
        "Request Type" to responseInfo?.requestType.orEmpty(),
        "Request Url" to responseInfo?.requestUrl.orEmpty(),
        "File Path" to responseInfo?.filePath,
        "Response Code" to responseInfo?.responseStatusCode.toString(),
        "Response Status" to responseInfo?.responseStatus.toString(),
        "Execution Time" to responseInfo?.executionTime.orEmpty(),
        "Error" to (responseInfo?.error ?: ""),
        "Request Headers" to responseInfo?.requestHeaders.orEmpty(),
        "Response Headers" to responseInfo?.responseHeaders.orEmpty(),
        "Request Body" to (responseInfo?.requestBody ?: ""),
        "Query Parameters" to (responseInfo?.queryParameters ?: ""),
        "Response Body" to (responseInfo?.responseBody ?: "")
      )
      Column {
        for ((key, value) in fields) {
          if (key =="File Path" ){
            Text(
              text = "$key: $value",
              modifier = Modifier
                .padding(bottom = 4.dp)
                .clickable {
                  onFilePathClicked(value ?: "")
                }
            )
          }else {
          Text(
            text = "$key: $value",
            modifier = Modifier.padding(bottom = 4.dp)
          )
          Divider()
        }}
      }
    }
  }
}

@Composable
fun PopupView(
  onClose: () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  AnimatedVisibility(
    visible = true,
  ) {

    Box(
      modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .border(width = 1.dp, brush = SolidColor(Color.White), shape = RoundedCornerShape(10.dp))
        .clip(RoundedCornerShape(LocalSpacing.current.spaceSmall))
        .background(Color.Transparent)
    ) {
      Column(
        modifier = Modifier
          .padding(LocalSpacing.current.spaceSmall)
          .align(Alignment.Center)
      ) {
        content()
        Spacer(modifier = Modifier.height(LocalSpacing.current.spaceSmall))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
        ) {

          BasicButton(onClick = onClose, value = stringResource(R.string.close_button))

        }

      }
    }
  }
}

