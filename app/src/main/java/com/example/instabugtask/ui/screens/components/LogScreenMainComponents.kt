package com.example.instabugtask.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instabugtask.R
import com.example.instabugtask.utils.fontfamilys.fontsfamilys

@Composable
fun OptionComponent( value :String,onCheckClicked: () -> Unit, checkedState : Boolean ,modifier: Modifier = Modifier) {

  var selectedState by remember { mutableStateOf(checkedState) }
  Row(
    Modifier
     .fillMaxWidth().heightIn()
      .toggleable(
        value = checkedState,
        onValueChange = {
          onCheckClicked()
          selectedState = it }
      )
      .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
      .background(color = Color.White, shape = RoundedCornerShape(size = 5.dp)),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(4.dp)
  ) {

    Checkbox(
      checked = checkedState,
      onCheckedChange = {
        selectedState = !it
      },
      modifier = modifier,
      colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.mainColor))
    )
    Text(
      text = value,
      style = TextStyle(
        fontSize = 12.sp,
        fontFamily = fontsfamilys.poppinsFamily,
        fontWeight = FontWeight(200),
        color = Color(0xCC000000),
      ),
      textAlign = TextAlign.Start,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis

    )

  }

}
